package com.lg.modle.function;

import java.time.Instant;

public class FuncReport {
    private int id;
    private int success = 0;
    private int failure = 0;
    private Instant launch_time;
    private Instant finish_time;
    private String tester = "YCKJ4444";

    public FuncReport() {
    }

    public FuncReport(int success, int failure) {
        this.success = success;
        this.failure = failure;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFailure() {
        return failure;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Instant getLaunch_time() {
        return launch_time;
    }

    public void setLaunch_time(Instant launch_time) {
        this.launch_time = launch_time;
    }

    public Instant getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(Instant finish_time) {
        this.finish_time = finish_time;
    }

    public String getTester() {
        return tester;
    }

    public void setTester(String tester) {
        this.tester = tester;
    }

    @Override
    public String toString() {
        return "FuncReport{" +
                "id=" + id +
                ", success=" + success +
                ", failure=" + failure +
                ", launch_time=" + launch_time +
                ", finish_time=" + finish_time +
                ", tester='" + tester + '\'' +
                '}';
    }
}
