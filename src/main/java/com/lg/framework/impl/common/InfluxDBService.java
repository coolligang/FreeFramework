package com.lg.framework.impl.common;

import com.lg.modle.common.Info;
import com.lg.modle.performance.PointData;
import com.lg.tool.common.ConfigPropertiesService;
import com.lg.tool.influxdb.InfluxDBHandler;

import java.util.HashMap;

public class InfluxDBService {
    private Info info;
    private InfluxDBHandler influxDBHandler;

    public InfluxDBService(Info info) {
        this.info = info;
        // 本地 influxDb 环境
        ConfigPropertiesService configPropertiesService = new ConfigPropertiesService("config.properties");
        String measurement = configPropertiesService.getProperty("db.measurement");
        String database = configPropertiesService.getProperty("db.database");
        String password = configPropertiesService.getProperty("db.password");
        String username = configPropertiesService.getProperty("db.username");
        String host = configPropertiesService.getProperty("db.host");
        this.influxDBHandler = new InfluxDBHandler(host, username, password, database, measurement);
    }

    public void insertAnalysisResult(PointData pointData) {
        HashMap<String, String> tags = new HashMap<>();
        tags.put("id", pointData.getTestId());
        tags.put("url", pointData.getUrl());
        tags.put("taskName", pointData.getTaskName());
        tags.put("threadCount", pointData.getThreadCount().toString());

        HashMap<String, Object> fields = new HashMap<>();
        fields.put("avgResponse", pointData.getAvgResponseTime());
        fields.put("TPS", pointData.getTps());
        fields.put("successRate", pointData.getSuccessRate());
        this.influxDBHandler.insert(tags, fields, pointData.getPointTime());
    }
}
