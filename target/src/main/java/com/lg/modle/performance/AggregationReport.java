package com.lg.modle.performance;

import com.lg.framework.impl.performance.ManagerServiceImpl;
import com.lg.modle.common.Info;

import java.time.Instant;

/**
 * 请求响应集合聚合结果对象
 */
public class AggregationReport {
    private String id;                        // 本次测试的id, 随机生成的16位字符串
    private String testName;                  // 测试名/测试计划名
    private String taskName;                  // 本次测试的任务名称
    private long start = -1;                  // 任务的开始时间
    private Instant startTime;                // 任务的开始时间
    private long end = -1;                    // 任务的结束时间
    private Instant endTime;                  // 任务的结束时间
    private Integer threadCount = -1;         // 当前线程数
    private long sumSuccess = 0;              // 成功总数
    private int sumRequest = 0;               // 请求总数
    private long sumData = 0;                 // 当前线程当前URL的请求数量总和
    private long sumDuring = 0;               // 当前线程当前URL的耗时总和(ms)
    private double avgResponse = -1;               // 平均响应时间
    private double avgTps = -1;                    // TPS
    private double successRate = 0;               // 成功率
    private double response50 = -1;                // 响应时间中位值，50%的响应时间优于这个时间
    private double response90 = -1;                // 响应时间90%分位
    private double response95 = -1;                // 响应时间95%分位
    private double response99 = -1;                // 响应时间99%分位
    private double maxResponse = -1;               // 最大响应时间
    private double minResponse = -1;               // 最小响应时间
    private ResourceMsg resourceMsg = new ResourceMsg();          // 当前并发数在本次时间内消耗的物理资源统计

    public AggregationReport() {
    }

    public AggregationReport(Info info) {
        this.id = info.getTestId();
        this.testName = info.getTestName();
    }

    public ResourceMsg getResourceMsg() {
        return resourceMsg;
    }

    public void setResourceMsg(ResourceMsg resourceMsg) {
        this.resourceMsg = resourceMsg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        if (this.taskName == null) {
            this.taskName = taskName;
        }
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        if (this.start < 0) {
            this.start = start;
            this.startTime = Instant.ofEpochMilli(start);
        }
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
        this.endTime = Instant.ofEpochMilli(end);
    }

    public Integer getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(Integer threadCount) {
        if (this.threadCount < 0) {
            this.threadCount = threadCount;
        }
    }

    public long getSumSuccess() {
        return sumSuccess;
    }

    public void setSumSuccess(long sumSuccess) {
        this.sumSuccess += sumSuccess;
    }

    public long getSumData() {
        return sumData;
    }

    public void setSumData(long sumData) {
        this.sumData += sumData;
    }

    public long getSumDuring() {
        return sumDuring;
    }

    public void setSumDuring(long sumDuring) {
        this.sumDuring += sumDuring;
    }

    public double getAvgResponse() {
        this.avgResponse = (double) sumDuring / (double) sumData;
        return avgResponse;
    }

    public void setAvgResponse(double avgResponse) {
        this.avgResponse = avgResponse;
    }

    public double getAvgTps() {
        this.avgTps = (double) sumData / (double) (sumDuring / 1000);
        return avgTps;
    }

    public void setAvgTps(double tps) {
        this.avgTps = tps;
    }

    public double getSuccessRate() {
        this.successRate = (double) this.sumSuccess / (double) this.sumData;
        return successRate;
    }

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public double getResponse50() {
        return response50;
    }

    public void setResponse50(double response50) {
        this.response50 = response50;
    }

    public double getResponse90() {
        return response90;
    }

    public void setResponse90(double response90) {
        this.response90 = response90;
    }

    public double getResponse95() {
        return response95;
    }

    public void setResponse95(double response95) {
        this.response95 = response95;
    }

    public double getMaxResponse() {
        return maxResponse;
    }

    public void setMaxResponse(double maxResponse) {
        this.maxResponse = maxResponse;
    }

    public double getMinResponse() {
        return minResponse;
    }

    public void setMinResponse(double minResponse) {
        this.minResponse = minResponse;
    }

    public double getResponse99() {
        return response99;
    }

    public void setResponse99(double response99) {
        this.response99 = response99;
    }

    public int getSumRequest() {
        return sumRequest;
    }

    public void setSumRequest(int sumRequest) {
        this.sumRequest = sumRequest;
    }

    @Override
    public String toString() {
        return "AggregationReport{" +
                "id='" + id + '\'' +
                ", taskName='" + taskName + '\'' +
                ", start=" + start +
                ", startTime=" + startTime +
                ", end=" + end +
                ", endTime=" + endTime +
                ", threadCount=" + threadCount +
                ", sumSuccess=" + sumSuccess +
                ", sumRequest=" + sumRequest +
                ", sumData=" + sumData +
                ", sumDuring=" + sumDuring +
                ", avgResponse=" + avgResponse +
                ", avgTps=" + avgTps +
                ", successRate=" + successRate +
                ", response50=" + response50 +
                ", response90=" + response90 +
                ", response95=" + response95 +
                ", response99=" + response99 +
                ", maxResponse=" + maxResponse +
                ", minResponse=" + minResponse +
                ", resourceMsg=" + resourceMsg +
                '}';
    }

    // CSV表头
    public static String getCSVHeader() {
        if (ManagerServiceImpl.prometheusService != null) {
            return "TestId,TestName,TaskName,ThreadCount,TotalRequest," +
                    "Tps,AvgResponse(ms),SuccessRate,Response99,Response95,Response90,Response50,MaxResponse,MinResponse," +
                    "EnvDesc,AvgCPU(%),AvgMemory(Mb)," +
                    "AvgGPU_UTL(%),AvgGPU_MEM(Mb),StartTime,EndTime\n";
        } else {
            return "TestId,TestName,TaskName,ThreadCount,TotalRequest," +
                    "Tps,AvgResponse(ms),SuccessRate,Response99,Response95,Response90,Response50,MaxResponse,MinResponse," +
                    "StartTime,EndTime\n";
        }
    }

    // CSV表正文
    public String toCSVRow() {
        if (ManagerServiceImpl.prometheusService != null) {
            return String.format("%s,%s,%s,%d,%s,%f,%f,%f,%f,%f,%f,%f,%f,%f,%s,%f,%f,%f,%f,%s,%s\n"
                    , id, testName, taskName, threadCount, sumRequest,
                    avgTps, avgResponse, successRate, response99, response95, response90, response50, maxResponse, minResponse,
                    resourceMsg.getEnvDesc(), resourceMsg.getAvgCPU(), resourceMsg.getAvgMemory(),
                    resourceMsg.getAvgGPU_UTL(), resourceMsg.getAvgGPU_MEM(), startTime, endTime);
        } else {
            return String.format("%s,%s,%s,%d,%s,%f,%f,%f,%f,%f,%f,%f,%f,%f,%s,%s\n"
                    , id, testName, taskName, threadCount, sumRequest,
                    avgTps, avgResponse, successRate, response99, response95, response90, response50, maxResponse, minResponse,
                    startTime, endTime);
        }
    }
}

