package com.lg.modle.stability;

import com.lg.modle.common.Info;
import com.lg.modle.performance.AggregationReport;

import java.util.ArrayList;

public class StabilityMsg {
    private String taskId;             // 任务Id
    private String id;                 // 测试Id
    private String description;        // 测试描述
    private String modelInfo;          // 模型信息
    private String callBackAddress;    // MPS 回调地址
    private StabilityData result;      // 数据
    private String host;              // 服务器所在IP


    public StabilityMsg(Info info) {
        this.id = info.getTestId();
        this.description = info.getTestName();
    }

    public StabilityMsg(Info info, AggregationReport aggregationReport) {
        this.id = info.getTestId();
        this.description = info.getTestName();
        this.result = new StabilityData(info, aggregationReport);
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModelInfo() {
        return modelInfo;
    }

    public void setModelInfo(String modelInfo) {
        this.modelInfo = modelInfo;
    }

    public String getCallBackAddress() {
        return callBackAddress;
    }

    public void setCallBackAddress(String callBackAddress) {
        this.callBackAddress = callBackAddress;
    }

    public StabilityData getResult() {
        return result;
    }

    public void setResult(StabilityData result) {
        this.result = result;
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "StabilityMsg{" +
                ", taskId='" + taskId + '\'' +
                ", id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", modelInfo='" + modelInfo + '\'' +
                ", callBackAddress='" + callBackAddress + '\'' +
                ", result=" + result +
                ", host=" + host +
                '}';
    }
}
