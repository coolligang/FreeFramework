package com.lg.modle.function;

import java.time.Instant;
import java.util.Arrays;

public class FuncData {
    private String caseName;              // 用例名
    private String[] caseStep;            // 用例步骤
    private boolean assertResult;         // 断言结果
    private String errMsg;                // 异常信息、断言信息
    private Instant startTime;
    private Instant endTime;


    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String[] getCaseStep() {
        return caseStep;
    }

    public void setCaseStep(String[] caseStep) {
        this.caseStep = caseStep;
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
        this.errMsg = errMsg;
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

    @Override
    public String toString() {
        return "FuncData{" +
                "caseName='" + caseName + '\'' +
                ", caseStep=" + Arrays.toString(caseStep) +
                ", assertResult=" + assertResult +
                ", errMsg='" + errMsg + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
