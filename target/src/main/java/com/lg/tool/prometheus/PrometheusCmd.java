package com.lg.tool.prometheus;

/**
 * ProtocolFreeFramework
 * PrometheusCmd
 * 封装物理资源的命令
 *
 * @author: ligang30
 * @date: 2022/6/22
 */

public class PrometheusCmd {
    private String cpuCmd;
    private String memoryCmd;
    private String gpuUtlCmd;
    private String gpuMemCmd;

    public String getCpuCmd() {
        return cpuCmd;
    }

    public void setCpuCmd(String cpuCmd) {
        this.cpuCmd = cpuCmd;
    }

    public String getMemoryCmd() {
        return memoryCmd;
    }

    public void setMemoryCmd(String memoryCmd) {
        this.memoryCmd = memoryCmd;
    }

    public String getGpuUtlCmd() {
        return gpuUtlCmd;
    }

    public void setGpuUtlCmd(String gpuUtlCmd) {
        this.gpuUtlCmd = gpuUtlCmd;
    }

    public String getGpuMemCmd() {
        return gpuMemCmd;
    }

    public void setGpuMemCmd(String gpuMemCmd) {
        this.gpuMemCmd = gpuMemCmd;
    }

    @Override
    public String toString() {
        return "PrometheusCmd{" +
                "cpuCmd='" + cpuCmd + '\'' +
                ", memoryCmd='" + memoryCmd + '\'' +
                ", gpuUtlCmd='" + gpuUtlCmd + '\'' +
                ", gpuMemCmd='" + gpuMemCmd + '\'' +
                '}';
    }
}
