package com.lg.framework.impl.performance;

import com.lg.modle.performance.WorkReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


/**
 * 任务类，添加任务继承此类
 */
public abstract class TasksObject implements Runnable {
    private static Logger logger = LogManager.getLogger(TasksObject.class);

    /**
     * 单次事务的前期准备工作
     */
    public void setUp() {
    }

    /**
     * 单次事务的收尾工作
     */
    public void tearDown() {
    }

    @Override
    public final void run() {
        while (ManagerServiceImpl.cachedThreadPool.isWorking()) {
            try {
                this.setUp();
                List<WorkReport> workReports = new ArrayList<>();
                Instant start = Instant.now();
                this.work(workReports);
                Instant end = Instant.now();
                this.tearDown();
                for (WorkReport workReport : workReports) {
                    if (workReport.getThreadCount() == null) {
                        workReport.setThreadCount(ManagerServiceImpl.cachedThreadPool.getPoolSize());
                    }
                    if (workReport.getTaskName() == null || workReport.getTaskName().equals("")) {
                        workReport.setTaskName(ManagerServiceImpl.cachedThreadPool.getTask().getTaskName());
                    }
                    if (workReport.getStart() == null) {
                        workReport.setStart(start);
                    }
                    if (workReport.getEnd() == null) {
                        workReport.setEnd(end);
                    }
                    ManagerServiceImpl.workReports.offer(workReport);
                }
            } catch (Exception e) {
                logger.error("TaskService[function run] error:{}", e.getMessage());
            }
        }
    }

    /**
     * 实现此方法，将测试过程写在这里
     *
     * @param workReports 存储 断言结果，异常信息，更准确的开始时间、结束时间，
     */
    public abstract void work(List<WorkReport> workReports);
}
