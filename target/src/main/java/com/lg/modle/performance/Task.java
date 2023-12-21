package com.lg.modle.performance;

import com.lg.framework.impl.performance.TaskObject;

public class Task {
    private String taskName;
    private TaskObject taskObject;
    private int min = -1;
    private int max = -1;
    private int step = -1; // 当step<0时，min=2^min  max=2^max
    private int runTime = -1;  // 当前任务的线程运行时长，当值为 -1 时，取Manager的 runTime

    public Task() {
    }

    public Task(String taskName, TaskObject taskObject) {
        this.taskName = taskName;
        this.taskObject = taskObject;
    }

    /**
     * @param taskName
     * @param taskObject
     * @param min        指数
     * @param max        指数
     */
    public Task(String taskName, TaskObject taskObject, int min, int max) {
        this.taskName = taskName;
        this.taskObject = taskObject;
        this.min = min;
        this.max = max;
    }

    /**
     * @param taskName
     * @param taskObject
     * @param min        具体的并发数
     * @param max        具体的并发数
     * @param step       步长
     */
    public Task(String taskName, TaskObject taskObject, int min, int max, int step) {
        this.taskName = taskName;
        this.taskObject = taskObject;
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public TaskObject getTaskObject() {
        return taskObject;
    }

    public void setTaskObject(TaskObject taskObject) {
        this.taskObject = taskObject;
    }


    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }
}
