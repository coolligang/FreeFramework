package com.lg.modle.stability;

import com.lg.tool.common.ConfigPropertiesService;
import com.lg.modle.common.Info;
import com.lg.modle.performance.AggregationReport;

public class StabilityData {
    private String tpsUrl;             // TPS
    private String responseUrl;        // 平均响应时间
    private String cpuUrl;             // CPU使用率
    private String memoryUrl;          // 内存使用
    private String gpuUrl;             // GPU 使用率
    private String gpuMemUrl;          // GPU 内存使用率
    private String ip;                 // ip地址

    public StabilityData(Info info, AggregationReport aggregationReport) {
        // 取grafana 的曲线 ，这里处理得并不通用，后面如需要再添加到配置文件中。
//        this.ip = info.getHost();
//        ConfigPropertiesService configPropertiesService = new ConfigPropertiesService("config.properties");
//        String gHost = configPropertiesService.getProperty("grafana.host");
//        String avgResponseUrl = "%s/d/24YciWpGz/stability?orgId=1&from=%d&to=%d&var-id=%s&var-taskName=%s&var-threadCount=%d&viewPanel=4&kiosk&theme=light";
//        this.responseUrl = String.format(avgResponseUrl, gHost, aggregationReport.getStart(), aggregationReport.getEnd(), aggregationReport.getId(), aggregationReport.getTaskName(), aggregationReport.getThreadCount());
//        String tpsUrl = "%s/d/24YciWpGz/stability?orgId=1&from=%d&to=%d&var-id=%s&var-taskName=%s&var-threadCount=%d&viewPanel=2&kiosk&theme=light";
//        this.tpsUrl = String.format(tpsUrl, gHost, aggregationReport.getStart(), aggregationReport.getEnd(), aggregationReport.getId(), aggregationReport.getTaskName(), aggregationReport.getThreadCount());
//        String cpuUrl = "%s/d/mX_5AipGz/container-resource?orgId=1&from=%d&to=%d&var-host=%s:7474&var-container=%s&viewPanel=2&kiosk&theme=light";
//        this.cpuUrl = String.format(cpuUrl, gHost, aggregationReport.getStart(), aggregationReport.getEnd(), info.getHost(), info.getContainerName());
//        String memoryUrl = "%s/d/mX_5AipGz/container-resource?orgId=1&from=%d&to=%d&var-host=%s:7474&var-container=%s&viewPanel=4&theme=light&kiosk";
//        this.memoryUrl = String.format(memoryUrl, gHost, aggregationReport.getStart(), aggregationReport.getEnd(), info.getHost(), info.getContainerName());
//        String gpuUrl = "%s/d/rTLfjipGz/container-gpu?orgId=1&from=%d&to=%d&var-host=%s:9400&var-uuid=%s&theme=light&viewPanel=2&kiosk";
//        this.gpuUrl = String.format(gpuUrl, gHost, aggregationReport.getStart(), aggregationReport.getEnd(), info.getHost(), info.getGpuId());
//        String gpuMemUrl = "%s/d/rTLfjipGz/container-gpu?orgId=1&from=%d&to=%d&var-host=%s:9400&var-uuid=%s&viewPanel=4&theme=light&kiosk";
//        this.gpuMemUrl = String.format(gpuMemUrl, gHost, aggregationReport.getStart(), aggregationReport.getEnd(), info.getHost(), info.getGpuId());
    }

    public String getTpsUrl() {
        return tpsUrl;
    }

    public void setTpsUrl(String tpsUrl) {
        this.tpsUrl = tpsUrl;
    }

    public String getResponseUrl() {
        return responseUrl;
    }

    public void setResponseUrl(String responseUrl) {
        this.responseUrl = responseUrl;
    }

    public String getCpuUrl() {
        return cpuUrl;
    }

    public void setCpuUrl(String cpuUrl) {
        this.cpuUrl = cpuUrl;
    }

    public String getMemoryUrl() {
        return memoryUrl;
    }

    public void setMemoryUrl(String memoryUrl) {
        this.memoryUrl = memoryUrl;
    }

    public String getGpuUrl() {
        return gpuUrl;
    }

    public void setGpuUrl(String gpuUrl) {
        this.gpuUrl = gpuUrl;
    }

    public String getGpuMemUrl() {
        return gpuMemUrl;
    }

    public void setGpuMemUrl(String gpuMemUrl) {
        this.gpuMemUrl = gpuMemUrl;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "StabilityData{" +
                "tpsUrl='" + tpsUrl + '\'' +
                ", responseUrl='" + responseUrl + '\'' +
                ", cpuUrl='" + cpuUrl + '\'' +
                ", memoryUrl='" + memoryUrl + '\'' +
                ", gpuUrl='" + gpuUrl + '\'' +
                ", gpuMemUrl='" + gpuMemUrl + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }


}
