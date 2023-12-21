package com.lg.tool.okhttp;

import okhttp3.*;

import java.io.IOException;

/**
 * ProtocolFreeFramework
 * okHttp
 *
 * @author: ligang30
 * @date: 2022/12/7
 */


public class OkHttpPostDemo {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static void main(String[] args) throws IOException {
        OkHttpPostDemo example = new OkHttpPostDemo();
        String json = "{'name':'二哥'}";
        String response = example.post("https://httpbin.org/post", json);
        System.out.println(response);
    }
}

