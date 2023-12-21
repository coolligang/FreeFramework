package com.lg.modle.performance;


/**
 * 物理资源数据对象
 */
public class ResourceMsg {
    private String envDesc = "";
    private double avgCPU = -1;
    private double avgMemory = -1;
    private double avgGPU_UTL = -1;
    private double avgGPU_MEM = -1;

    public ResourceMsg() {
    }

    public ResourceMsg(double avgCPU, double avgMemory, double avgGPU_UTL, double avgGPU_MEM) {
        this.avgCPU = avgCPU;
        this.avgMemory = avgMemory;
        this.avgGPU_UTL = avgGPU_UTL;
        this.avgGPU_MEM = avgGPU_MEM;
    }

    public String getEnvDesc() {
        return envDesc;
    }

    public void setEnvDesc(String envDesc) {
        this.envDesc = envDesc;
    }

    public double getAvgCPU() {
        return avgCPU;
    }

    public void setAvgCPU(double avgCPU) {
        this.avgCPU = avgCPU;
    }

    public double getAvgMemory() {
        return avgMemory;
    }

    public void setAvgMemory(double avgMemory) {
        this.avgMemory = avgMemory;
    }

    public double getAvgGPU_UTL() {
        return avgGPU_UTL;
    }

    public void setAvgGPU_UTL(double avgGPU_UTL) {
        this.avgGPU_UTL = avgGPU_UTL;
    }

    public double getAvgGPU_MEM() {
        return avgGPU_MEM;
    }

    public void setAvgGPU_MEM(double avgGPU_MEM) {
        this.avgGPU_MEM = avgGPU_MEM;
    }

    @Override
    public String toString() {
        return "ResourceMsg{" +
                ", avgCPU=" + avgCPU +
                ", avgMemory=" + avgMemory +
                ", avgGPU_UTL=" + avgGPU_UTL +
                ", avgGPU_MEM=" + avgGPU_MEM +
                '}';
    }
}
