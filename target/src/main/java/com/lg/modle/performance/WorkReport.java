package com.lg.modle.performance;

import com.lg.modle.common.Status;

import java.time.Instant;

/**
 * Instant 格式化
 * DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)
 * .withLocale(Locale.getDefault())
 * .withZone(ZoneId.systemDefault());
 * System.out.println(Instant.now());
 * System.out.println(formatter.format(Instant.now()));
 */

public class WorkReport {
    public long startTime;             // 线程开始时间
    public Instant start;              // 线程开始时间(UTC)
    public long endTime;               // 线程结束时间
    public Instant end;                // 线程结束时间
    public long during;                // 单次请求响应时间（毫秒）
    public boolean assertResult = true;   // 断言结果
    public String errMsg;              // 断言错误信息，正确为空
    public Integer threadCount;        // 当前线程池容量
    public String taskName;            // 事务名称
    public String URL = "null";        // URL / function name
    public long send = 0;              // 发送的字节数
    public long received = 0;          // 接收的字节数

    private int state = Status.testWORKING;     // 标记阶段性状态

    public WorkReport() {
    }

    public WorkReport(int count) {
        this.threadCount = count;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isAssertResult() {
        return assertResult;
    }

    public void setAssertResult(boolean assertResult) {
        this.assertResult = assertResult;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = this.errMsg == null || this.errMsg.equals("") ? errMsg : this.errMsg + "\n" + errMsg;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public Integer getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(Integer threadCount) {
        this.threadCount = threadCount;
    }

    public long getDuring() {
        return during;
    }

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
//        Instant rStart = start.plusMillis(TimeUnit.HOURS.toMillis(8));
        this.start = start;
        this.startTime = start.toEpochMilli();
    }

    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
//        Instant rEnd = end.plusMillis(TimeUnit.HOURS.toMillis(8));
        if (this.end == null) {
            this.end = end;
            this.endTime = end.toEpochMilli();
            if (this.startTime > 0) {
                this.during = this.endTime - this.startTime;
            }
        }
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public long getSend() {
        return send;
    }

    public void setSend(long send) {
        this.send = send;
    }

    public long getReceived() {
        return received;
    }

    public void setReceived(long received) {
        this.received = received;
    }

    @Override
    public String toString() {
        return "WorkReport{" +
                "startTime=" + startTime +
                ", start=" + start +
                ", endTime=" + endTime +
                ", end=" + end +
                ", during=" + during +
                ", assertResult=" + assertResult +
                ", errMsg='" + errMsg + '\'' +
                ", threadCount=" + threadCount +
                ", taskName='" + taskName + '\'' +
                ", URL='" + URL + '\'' +
                ", state=" + state +
                '}';
    }
}
