package com.lg.framework.impl.performance;

import com.lg.modle.performance.WorkReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * 任务类，添加任务继承此类
 */
public abstract class TaskObject<V> implements Runnable {
    private static Logger logger = LogManager.getLogger(TaskObject.class);
    private V otherData;
    private List<V> otherList;
    private HashMap<String, V> otherMap;

    public V getOtherData() {
        return otherData;
    }

    public void setOtherData(V otherData) {
        this.otherData = otherData;
    }

    public HashMap<String, V> getOtherMap() {
        return otherMap;
    }

    public void setOtherMap(HashMap<String, V> otherMap) {
        this.otherMap = otherMap;
    }

    public List<V> getOtherList() {
        return otherList;
    }

    public void setOtherList(List<V> otherList) {
        this.otherList = otherList;
    }

    /**
     * 单次事务的前期准备工作
     */
    public abstract void setUp();

    /**
     * 单次事务的收尾工作
     */
    public abstract void tearDown();

    @Override
    public final void run() {
        while (ManagerServiceImpl.cachedThreadPool.isWorking()) {
            WorkReport workReport = new WorkReport();
            workReport.setThreadCount(ManagerServiceImpl.cachedThreadPool.getPoolSize());
            this.setUp();
            workReport.setTaskName(ManagerServiceImpl.cachedThreadPool.getTask().getTaskName());
            workReport.setStart(Instant.now());
            try {
                this.work(workReport);
            } catch (Exception e) {
                workReport.setAssertResult(false);
                workReport.setErrMsg(Arrays.toString(e.getStackTrace()) + " --> " + e.getMessage());
            }
            if (workReport.getEnd() == null) {
                workReport.setEnd(Instant.now());
            }
            ManagerServiceImpl.workReports.offer(workReport);
            this.tearDown();
        }
    }

    /**
     * 实现此方法，将测试过程写在这里
     *
     * @param workReport 存储 断言结果，异常信息，更准确的开始时间、结束时间，
     */
    public abstract void work(WorkReport workReport) throws AssertionError;

}
