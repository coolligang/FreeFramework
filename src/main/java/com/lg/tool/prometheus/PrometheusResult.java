package com.lg.tool.prometheus;

public class PrometheusResult {

    private double avgResult = 0;
    private String detail = "[]";

    public PrometheusResult() {
    }

    public PrometheusResult(double result, String detail) {
        this.avgResult = result;
        this.detail = detail;
    }

    public double getAvgResult() {
        return avgResult;
    }

    public void setAvgResult(double avgResult) {
        this.avgResult = avgResult;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "PrometheusResult{" +
                "avgResult=" + avgResult +
                ", detail='" + detail + '\'' +
                '}';
    }
}
