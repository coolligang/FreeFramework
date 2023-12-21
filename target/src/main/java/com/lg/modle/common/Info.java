package com.lg.modle.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Info {
    private String testId;            // 本次测试的id, 随机生成的16位字符串
    private String testName;          // 测试名称/测试计划

    public Info() {
    }

    public Info(String testName) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        this.testId = formatter.format(new Date());
        System.out.println("testId=" + this.testId);
        this.testName = testName;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }


    @Override
    public String toString() {
        return "Info{" +
                ", testId='" + testId + '\'' +
                ", testName='" + testName + '\'' +
                '}';
    }
}
