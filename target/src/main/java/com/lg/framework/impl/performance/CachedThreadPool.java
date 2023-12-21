package com.lg.framework.impl.performance;

import com.lg.modle.common.Status;
import com.lg.modle.performance.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public final class CachedThreadPool {
    private static Logger logger = LogManager.getLogger(CachedThreadPool.class);
    private ExecutorService pool;
    private int poolSize;
    private long startTime;
    private Task task;               // 事务
    private boolean working;
    private List<TaskObject> tasks;  // 任务列表，用于跑稳定性

    public CachedThreadPool(Task task, int poolSize) {
        this.poolSize = poolSize;
        this.working = Status.threadSTOP;
        this.task = task;
        this.pool = Executors.newCachedThreadPool();
    }

    public CachedThreadPool(List<Task> tasks, int poolSize) {
        this.poolSize = poolSize;
        this.tasks = new ArrayList<>();
        tasks.forEach(taskEntity -> {
            try {
                TaskObject taskObject = taskEntity.getTaskObject().getClass().newInstance();
                this.tasks.add(taskObject);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        this.pool = Executors.newCachedThreadPool();
    }

    public Task getTask() {
        return this.task;
    }

    public void runTask() {
        this.working = Status.threadRUNNING;
        try {
            // 反射TaskObject的无参构造方法
            TaskObject taskObject = this.task.getTaskObject().getClass().newInstance();
            // 记录线程开始时间
            this.startTime = System.currentTimeMillis();
            // 开始添加线程
            for (int i = 0; i < poolSize; i++) {
                // 线程加入到线程池
                this.pool.submit(taskObject);
            }
        } catch (IllegalAccessException | InstantiationException e) {
            logger.error("Function[runTask] error\n");
            e.printStackTrace();
        }
    }

    public boolean isWorking() {
        return working;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }

    public ExecutorService getPool() {
        return pool;
    }

    // 获取当前线程池最大线程数
    public int getPoolSize() {
        return poolSize;
    }

    // 获取当前线程池活动线程数
    public int getActiveCount() {
        return ((ThreadPoolExecutor) this.pool).getActiveCount();
    }

    public long getDuring() {
        return this.startTime != 0 ? System.currentTimeMillis() - this.startTime : -1;
    }

    public void terminated() {
        // 关闭施压阀
        this.working = Status.threadSTOP;
        // 停止线程池
        this.pool.shutdown();
        // 等待线程池任务执行完毕
        while (!this.pool.isTerminated()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
