package com.lg.modle.performance;

import com.lg.modle.common.Info;

import java.util.List;

public class PointData {
    private String testId = "";
    private String taskName = "";
    private long pointTime = 0;
    private String url = "";
    private double tps = 0;
    private double avgResponseTime = 0;
    private Integer threadCount = 0;
    private double successRate = 0;
    private List<String> errMsg;

    public PointData() {
    }

    public PointData(Info info) {
        this.testId = info.getTestId();
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public long getPointTime() {
        return pointTime;
    }

    public void setPointTime(long pointTime) {
        this.pointTime = pointTime;
    }

    public double getTps() {
        return tps;
    }

    public void setTps(double tps) {
        this.tps = (double) Math.round(tps * 100) / 100;
    }

    public double getAvgResponseTime() {
        return avgResponseTime;
    }

    public void setAvgResponseTime(double avgResponseTime) {
        this.avgResponseTime = (double) Math.round(avgResponseTime * 100) / 100;
    }

    public Integer getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(Integer threadCount) {
        this.threadCount = threadCount;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(double successRate) {
        this.successRate = (double) Math.round(successRate * 10000) / 10000;
    }

    public List<String> getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(List<String> errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return "PointData{" +
                "testId='" + testId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", pointTime=" + pointTime +
                ", url='" + url + '\'' +
                ", tps=" + tps +
                ", avgResponseTime=" + avgResponseTime +
                ", threadCount=" + threadCount +
                ", successRate=" + successRate +
                '}';
    }
}
