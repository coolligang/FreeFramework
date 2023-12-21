package com.lg.test;

import com.lg.tool.httpClient.HttpHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * ProtocolFreeFramework
 * HttpTest
 *
 * @author: ligang30
 * @date: 2022/10/12
 */

public class HttpTest {
    public static void main(String[] args) {
        HttpHelper httpHelper = new HttpHelper();
        String url = "https://ma.mrrjvip.com/Account/Login";
        Map<String, Object> body = new HashMap<>();
        body.put("", "");
        Map<String, String> header = new HashMap<>();
        header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        header.put("Accept-Encoding", "gzip, deflate, br");
        header.put("Connection", "keep-alive");
        header.put("Content-Type", "application/x-www-form-urlencoded");
        header.put("Cookie", "aliyungf_tc=afe1dab6e87e2eebc645ca89ff82f1c6f9dc3a5d1970caf58aa269a43f08b6ee; __RequestVerificationToken=7S_juC1Q2CaWczXFKs7dBDCUuyIUkfTjmuT1BUErPiDyw8OatPtqcG91KEezMowzvMcs49b7h6X5kN3rQgGJ0DVGOJq0c30-_BSbyxnUano1; ASP.NET_SessionId=efjsridrlfl4jw1adru5q1ot");
        header.put("Host", "ma.mrrjvip.com");
        header.put("Origin", "https://ma.mrrjvip.com");
        httpHelper.post(url, body, header);
    }
}
