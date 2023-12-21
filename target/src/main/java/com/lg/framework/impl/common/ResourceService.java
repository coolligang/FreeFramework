package com.lg.framework.impl.common;

import com.lg.framework.impl.performance.ManagerServiceImpl;
import com.lg.modle.common.Info;
import com.lg.modle.performance.ResourceMsg;
import com.lg.tool.prometheus.PrometheusResult;
import com.lg.tool.prometheus.ResourceInfo;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ResourceService {
    private Info info;

    public ResourceService(Info info) {
        this.info = info;
    }

    public List<ResourceMsg> getResourceInfo(Instant start, Instant end) {
        // 重置prometheus的开始结束时间，
        List<ResourceMsg> resourceMsgs = new ArrayList<>();
        for (ResourceInfo resourceInfo : ManagerServiceImpl.prometheusService.getResourceInfos()) {
            // 实例化 ResourceMsg
            ResourceMsg resourceMsg = new ResourceMsg();
            resourceMsg.setEnvDesc(resourceInfo.getEnvDesc());  // 环境资源描述

            // get CPU
            if (resourceInfo.getPrometheusCmd().getCpuCmd() == null || resourceInfo.getPrometheusCmd().getCpuCmd().equals("")) {
                resourceMsg.setAvgCPU(0);
            } else {
                PrometheusResult cpuResult = ManagerServiceImpl.prometheusService.getValue(resourceInfo.getPrometheusCmd().getCpuCmd(), start, end);
                resourceMsg.setAvgCPU(cpuResult != null ? cpuResult.getAvgResult() : 0);
            }
            // get Mem
            if (resourceInfo.getPrometheusCmd().getMemoryCmd() == null || resourceInfo.getPrometheusCmd().getMemoryCmd().equals("")) {
                resourceMsg.setAvgMemory(0);
            } else {
                PrometheusResult memResult = ManagerServiceImpl.prometheusService.getValue(resourceInfo.getPrometheusCmd().getMemoryCmd(), start, end);
                resourceMsg.setAvgMemory(memResult != null ? memResult.getAvgResult() : 0);
            }
            // get gpu UTL
            if (resourceInfo.getPrometheusCmd().getGpuUtlCmd() == null || resourceInfo.getPrometheusCmd().getGpuUtlCmd().equals("")) {
                resourceMsg.setAvgGPU_UTL(0);
            } else {
                PrometheusResult UTLResult = ManagerServiceImpl.prometheusService.getValue(resourceInfo.getPrometheusCmd().getGpuUtlCmd(), start, end);
                resourceMsg.setAvgGPU_UTL(UTLResult != null ? UTLResult.getAvgResult() : 0);
            }
            // get GPU_MEM
            if (resourceInfo.getPrometheusCmd().getGpuMemCmd() == null || resourceInfo.getPrometheusCmd().getGpuMemCmd().equals("")) {
                resourceMsg.setAvgGPU_MEM(0);
            } else {
                PrometheusResult MEMResult = ManagerServiceImpl.prometheusService.getValue(resourceInfo.getPrometheusCmd().getGpuMemCmd(), start, end);
                resourceMsg.setAvgGPU_MEM(MEMResult != null ? MEMResult.getAvgResult() : 0);
            }
            // 入列
            resourceMsgs.add(resourceMsg);
        }
        return resourceMsgs;
    }
}
