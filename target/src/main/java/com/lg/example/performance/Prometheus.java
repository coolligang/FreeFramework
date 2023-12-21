package com.lg.example.performance;

import com.lg.tool.prometheus.PrometheusCmd;
import com.lg.tool.prometheus.PrometheusResult;
import com.lg.tool.prometheus.PrometheusService;
import com.lg.tool.prometheus.ResourceInfo;

import java.time.Instant;

/**
 * BlockchainPerformance
 * Prometheus
 *
 * @author: ligang30
 * @date: 2022/6/24
 */

public class Prometheus {
    String prometheusHost = "http://43.243.151.117:30057";
    String cpu = "rate(container_cpu_user_seconds_total{instance=\"%s:10255\",container=\"%s\",namespace=\"%s\"}[90s])*100";
    String memory = "container_memory_rss{instance=\"%s:10255\",container=\"%s\",namespace=\"%s\"}/1048576";
    ResourceInfo gatewayInfo;

    public Prometheus() {
        String instance = "10.220.196.48";
        String container = "contract-gateway-controller";
        String namespace = "baas-blockchain";
        PrometheusCmd gatewayPrometheusCmd = new PrometheusCmd();
        gatewayPrometheusCmd.setCpuCmd(String.format(cpu, instance, container, namespace));
        gatewayPrometheusCmd.setMemoryCmd(String.format(memory, instance, container, namespace));
        this.gatewayInfo = new ResourceInfo("Gateway[48]", gatewayPrometheusCmd);
    }

    /**
     *
     */

    /**
     * 合约网关部署情况
     * Gateway
     * namespace: baas-blockchain
     * contractPod: contract-gateway-controller
     * container: 10.220.196.48 -
     * AntGM
     * namespace: 148812558106625
     * contractPod: ant-contract-gateway-deployment-75b6658fc6-77pl8
     * container: 10.220.196.49 - ant-contract-gateway
     * <p>
     * AntFGM
     * namespace: 150265632784385
     * contractPod: ant-contract-gateway-deployment-75b6658fc6-xv8q2
     * container: 10.220.196.49 - ant-contract-gateway
     * <p>
     * FabricGM
     * namespace: vai679jz8sl
     * contractPod: chaincode-gateway-5c449b8b77-h9zds
     * container: 10.220.196.49 - chaincode-gateway
     */
    public PrometheusService getPrometheusService(String instance, String containerName, String namespace) {
        PrometheusCmd prometheusCmd = new PrometheusCmd();
        prometheusCmd.setCpuCmd(String.format(cpu, instance, containerName, namespace));
        prometheusCmd.setMemoryCmd(String.format(memory, instance, containerName, namespace));
        ResourceInfo resourceInfo = new ResourceInfo(instance + "-" + containerName, prometheusCmd);

        PrometheusService prometheusService = new PrometheusService(prometheusHost);
        prometheusService.addResourceInfo(resourceInfo);

        // 添加默认网关的资源
        prometheusService.addResourceInfo(this.gatewayInfo);
        return prometheusService;
    }


    public static void main(String[] args) {
        PrometheusService prometheusService = new Prometheus().getPrometheusService("10.220.196.49", "chaincode-gateway", "vai679jz8sl");
        Instant start = Instant.parse("2022-07-08T14:00:50.451Z");
        Instant end = Instant.parse("2022-07-08T14:01:10.489Z");

        for (ResourceInfo resourceInfo : prometheusService.getResourceInfos()) {
            System.out.println(resourceInfo);
            PrometheusResult cpu = prometheusService.getValue(resourceInfo.getPrometheusCmd().getCpuCmd(), start, end);
            System.out.println("cpu cmd= " + resourceInfo.getPrometheusCmd().getCpuCmd());
            System.out.println("cpu= " + cpu.getDetail());
            System.out.println("cpu= " + cpu.getAvgResult());
            PrometheusResult memory = prometheusService.getValue(resourceInfo.getPrometheusCmd().getMemoryCmd(), start, end);
            System.out.println("memory cmd= " + resourceInfo.getPrometheusCmd().getMemoryCmd());
            System.out.println("memory= " + memory.getAvgResult());
            System.out.println("memory= " + memory.getDetail());
            System.out.println("==================================");
        }
    }
}
