package com.lg.modle.function;

import java.time.Instant;
import java.util.Arrays;

public class FuncDataMsg {
    private int pid;                 // reportId
    private String name;             // 用例名
    private boolean passed;          // 断言结果
    private String message;          // 异常信息
    private String doc;              // 步骤信息
    private Instant begin_time;
    private Instant end_time;

    public FuncDataMsg(int pid, FuncData funcData) {
        this.pid = pid;
        this.name = funcData.getCaseName();
        this.passed = funcData.isAssertResult();
        this.message = funcData.getErrMsg();
        this.doc = Arrays.toString(funcData.getCaseStep());
        this.begin_time = funcData.getStartTime();
        this.end_time = funcData.getEndTime();
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public Instant getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(Instant begin_time) {
        this.begin_time = begin_time;
    }

    public Instant getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Instant end_time) {
        this.end_time = end_time;
    }

    @Override
    public String toString() {
        return "FuncDataMsg{" +
                "pid=" + pid +
                ", name='" + name + '\'' +
                ", passed=" + passed +
                ", message='" + message + '\'' +
                ", doc='" + doc + '\'' +
                ", begin_time=" + begin_time +
                ", end_time=" + end_time +
                '}';
    }
}
