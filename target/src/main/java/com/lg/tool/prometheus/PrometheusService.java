package com.lg.tool.prometheus;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lg.tool.httpClient.HttpHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrometheusService {
    private String host;    // prometheus ip:port
    private List<ResourceInfo> resourceInfos;
    private HttpHelper httpHelper = new HttpHelper();

    private static Logger logger = LogManager.getLogger(PrometheusService.class);

    public PrometheusService() {
    }

    public PrometheusService(String prometheusHost) {
        this.setHost(prometheusHost);
    }

    public PrometheusService(String prometheusHost, List<ResourceInfo> resourceInfos) {
        this.setHost(prometheusHost);
        this.resourceInfos = resourceInfos;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        if (!host.endsWith("/")) {
            host = host + "/";
        }
        this.host = host;
    }


    public List<ResourceInfo> getResourceInfos() {
        return resourceInfos;
    }

    public void setResourceInfos(List<ResourceInfo> resourceInfos) {
        this.resourceInfos = resourceInfos;
    }

    public void addResourceInfo(ResourceInfo resourceInfo) {
        if (this.resourceInfos == null) {
            this.resourceInfos = new ArrayList<>();
        }
        this.resourceInfos.add(resourceInfo);
    }

    public PrometheusResult getValue(String queryStr, Instant start, Instant end) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("query", queryStr);
        paramMap.put("start", start.toString());
        paramMap.put("end", end.toString());
        paramMap.put("step", "1s");
        try {
            String response = this.httpHelper.get(this.host + "api/v1/query_range", paramMap, HttpHelper.getDefaultHeader());
            JSONObject jsonObject = JSON.parseObject(response);
            String status = jsonObject.getString("status");
            if (status.equals("success")) {
                JSONArray resultJson = jsonObject.getJSONObject("data").getJSONArray("result");
                if (resultJson.size() > 0) {
                    JSONArray detail = resultJson.getJSONObject(0).getJSONArray("values");
                    double sum = 0;
                    for (int i = 0; i < detail.size(); i++) {
                        double value = detail.getJSONArray(i).getDoubleValue(1);
                        sum += value;
                    }
                    double result = sum / (double) detail.size();
                    return new PrometheusResult(result, detail.toJSONString());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        // 如果没有返回预期的值，返回一个默认的 PrometheusResult
        return new PrometheusResult();
    }
}

