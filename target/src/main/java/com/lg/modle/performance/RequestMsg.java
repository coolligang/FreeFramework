package com.lg.modle.performance;

import com.lg.modle.common.Info;

public class RequestMsg {
    private String testId;                             // 本次测试的id, 随机生成的16位字符串
    private String testName;                           // 测试名称，描述
    private String host;                               // 服务IP列表
    private String prometheusAddress;                  // prometheus地址
    private AggregationReport report;                  // 单个线程测试结果

    public RequestMsg() {
    }

    public RequestMsg(Info info) {
        this.testId = info.getTestId();
        this.testName = info.getTestName();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPrometheusAddress() {
        return prometheusAddress;
    }

    public void setPrometheusAddress(String prometheusAddress) {
        this.prometheusAddress = prometheusAddress;
    }

    public AggregationReport getReport() {
        return report;
    }

    public void setReport(AggregationReport report) {
        this.report = report;
    }

    @Override
    public String toString() {
        return "RequestMsg{" +
                "testId='" + testId + '\'' +
                ", testName='" + testName + '\'' +
                ", host='" + host + '\'' +
                ", prometheusAddress='" + prometheusAddress + '\'' +
                ", report=" + report +
                '}';
    }

}