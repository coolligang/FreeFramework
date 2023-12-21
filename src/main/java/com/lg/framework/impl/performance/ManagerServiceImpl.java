package com.lg.framework.impl.performance;

import com.lg.framework.service.ManagerService;
import com.lg.modle.common.Info;
import com.lg.modle.common.Status;
import com.lg.modle.performance.Task;
import com.lg.modle.performance.WorkReport;
import com.lg.tool.prometheus.PrometheusService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.*;


public abstract class ManagerServiceImpl implements ManagerService {
    private Info info;
    private static Logger logger = LogManager.getLogger(ManagerServiceImpl.class);
    private List<Task> tasks;                           // 性能任务列表
    private int runTime = 60;                           // 单组线程运行时间(单位：秒), [稳定性时,单位：分钟，当小于等于0时，持续运行]
    private int min = 0;                                // 最小线程指数, 默认为0
    private int max = 10;                               // 最大线程指数, 默认为10
    private int step = -1;                              // 线程增加的步长，当该值<0时，min=2^min  max=2^max
    private DataServiceFactory dataServiceFactory;      // 数据工厂

    public static boolean saveAssertMsg = false;        // 保存错误日志,默认不保存
    public static String reportPath = "logs";           // 默认将日志保存到logs目录
    public static Queue<WorkReport> workReports = new ConcurrentLinkedQueue<>();
    public static CachedThreadPool cachedThreadPool;    // 线程池
    public static boolean firstTask = true;             // 第一个任务
    public static PrometheusService prometheusService;  // Prometheus 服务

    public ManagerServiceImpl() {
    }

    /**
     * 管理器构造方法
     *
     * @param info // 测试信息
     */
    public ManagerServiceImpl(Info info) {
        this.info = info;
        dataServiceFactory = new DataServiceFactory(info);
    }

    public ManagerServiceImpl(Info info, int runTime) {
        this.info = info;
        this.runTime = runTime;
        dataServiceFactory = new DataServiceFactory(info);
    }

    public ManagerServiceImpl(Info info, List<Task> tasks, int runTime) {
        this.info = info;
        this.tasks = tasks;
        this.runTime = runTime;
        dataServiceFactory = new DataServiceFactory(info);
    }

    public String getReportPath() {
        return reportPath;
    }

    public void setReportPath(String reportPath) {
        if (!reportPath.equals("")) {
            ManagerServiceImpl.reportPath = reportPath;
        }
    }

    public void setPrometheusService(PrometheusService prometheusService) {
        this.prometheusService = prometheusService;
    }

    public PrometheusService getPrometheusService() {
        return this.prometheusService;
    }

    public void saveAssertMsg(boolean is) {
        saveAssertMsg = is;
    }

    // 稳定性并发数为2的指数
//    public void setFixedPower(int fixedPower) {
//        this.fixed = fixedPower == 0 ? 1 : 2 << (fixedPower - 1);
//    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public void setMinPower(int power) {
        this.min = power;
    }

    public void setMaxPower(int power) {
        this.max = power;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public void addTaskObject(Task task) {
        if (this.tasks == null) {
            this.tasks = new ArrayList<>();
        }
        this.tasks.add(task);
    }

    /**
     * 在执行计划前的准备工作
     */
    public abstract void classBefore();

    /**
     * 执行完计划后的收尾工作
     */
    public abstract void classEnd();

    private void sendFlag(int flag, int poolSize) {
        WorkReport workReport = new WorkReport();
        workReport.setState(flag);
        workReport.setTaskName(cachedThreadPool.getTask().getTaskName());
        workReport.setThreadCount(poolSize);
        workReports.offer(workReport);
    }

    /**
     * 执行性能计划
     */
    private void planExecute() {
        this.tasks.forEach((task) -> {
            logger.info("***************************************************");
            logger.info("===== 开始执行任务{} =====", task.getTaskName());
            logger.info("***************************************************");
            // 开启接收数据队列
            this.dataServiceFactory.getDataService().startNow();
            // 设置任务的并发区间, 没有对当前task设置最大最小并发数时[必须同时设置]，使用manager默认设置
            if (task.getMin() < 0 || task.getMax() < 0) {
                task.setMin(this.min);
                task.setMax(this.max);
                task.setStep(this.step);
            }
            // 没有设置线程增加步长的情况下，默认为 2^N 指数增长
            int startSize;
            int endSize;
            if (task.getStep() < 0) {
                startSize = task.getMin() == 0 ? 1 : 2 << (task.getMin() - 1);
                endSize = task.getMax() == 0 ? 1 : 2 << (task.getMax() - 1);
            } else { // 设置了线程增加的步长
                startSize = task.getMin();
                endSize = task.getMax();
            }
            // 开始创建线程池，持续加压
            for (int poolSize = startSize; poolSize <= endSize; poolSize = task.getStep() > 0 ? poolSize + task.getStep() : poolSize * 2) {
                cachedThreadPool = new CachedThreadPool(task, poolSize);
                cachedThreadPool.runTask();
                int taskRunTime = task.getRunTime() > 0 ? task.getRunTime() : this.runTime;
                if (taskRunTime > 0) {
                    // 检查运行时间
                    while (cachedThreadPool.getDuring() < taskRunTime * 1000) {
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            logger.error(Arrays.toString(e.getStackTrace()) + " --> " + e.getMessage());
                        }
                    }
                }
                // 关闭线程池
                cachedThreadPool.terminated();
                this.sendFlag(Status.testSUSPEND, poolSize);
            }
            // 发送一个结束状态数据
            this.sendFlag(Status.testEND, -1);
        });
    }


    /**
     * 顺序执行任务
     * 这里需要优化，稳定性只有一个任务，不需要顺序执行
     * 或者，多个任务，按比例去执行
     */
    public final void go() {
        try {
            this.classBefore();
            // 性能测试
            if (this.tasks.size() <= 0) {
                logger.error("No tasks~");
            } else {
                this.planExecute();
            }
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()) + " --> " + e.getMessage());
        } finally {
            try {
                this.classEnd();
            } catch (Exception e) {
                logger.error("teardown error:{}", e.getMessage());
            }
        }
    }
}
