package com.lg.tool.prometheus;

import java.util.List;

/**
 * ProtocolFreeFramework
 * ResourceObj
 * 硬件对象
 *
 * @author: ligang30
 * @date: 2022/6/22
 */

public class ResourceInfo {
    private String envDesc;         // 环境描述
    private PrometheusCmd prometheusCmd;

    public ResourceInfo() {
    }

    public ResourceInfo(String envDesc, PrometheusCmd prometheusCmd) {
        this.envDesc=envDesc;
        this.prometheusCmd=prometheusCmd;
    }

    public String getEnvDesc() {
        return envDesc;
    }

    public void setEnvDesc(String envDesc) {
        this.envDesc = envDesc;
    }

    public PrometheusCmd getPrometheusCmd() {
        return prometheusCmd;
    }

    public void setPrometheusCmd(PrometheusCmd prometheusCmd) {
        this.prometheusCmd = prometheusCmd;
    }

    @Override
    public String toString() {
        return "ResourceInfo{" +
                "envDesc='" + envDesc + '\'' +
                ", prometheusCmd=" + prometheusCmd +
                '}';
    }
}
