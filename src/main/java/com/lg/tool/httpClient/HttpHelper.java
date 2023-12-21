package com.lg.tool.httpClient;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HttpHelper {
    private static Logger logger = LogManager.getLogger(HttpHelper.class);
    private CloseableHttpClient httpClient;
    public static final String response = "Response";
    public static final String send = "Send";
    public static final String received = "Received";

    public HttpHelper() {
        this.httpClient = HttpClients.createDefault();
    }

    public static HashMap<String, String> getDefaultHeader() {
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("accept", "*/*");
        header.put("connection", "Keep-Alive");
        header.put("Content-Type", "application/json");
//      header.put("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        return header;
    }

    public static String getAuthorization(String user, String password) {
        String auth = user + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
        return "Basic " + new String(encodedAuth);
    }

    public String post(String url, String body, Map<String, String> header) {
        String result = "";
        HttpPost post = new HttpPost(url);
        try {
//             设置超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(20 * 1000)
                    .setConnectTimeout(20 * 1000)
                    .setSocketTimeout(20 * 1000)
                    .build();
            post.setConfig(requestConfig);
            if (header != null) {
                header.forEach(post::setHeader);
            }
            StringEntity postingString = new StringEntity(body, StandardCharsets.UTF_8);
            post.setEntity(postingString);
            HttpResponse response = this.httpClient.execute(post);

            InputStream in = response.getEntity().getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            StringBuilder strber = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                //strber.append(line).append('\n');
                strber.append(line);
            }
            br.close();
            in.close();
            result = strber.toString();
        } catch (Exception e) {
            logger.error("[String]Post error: {}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            post.abort();
        }
        return result;
    }

    public String post(String url, Map<String, Object> body, Map<String, String> header) {
        String result = "";
        HttpPost post = new HttpPost(url);
        try {
//             设置超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(20 * 1000)
                    .setConnectTimeout(20 * 1000)
                    .setSocketTimeout(20 * 1000)
                    .build();
            post.setConfig(requestConfig);
            if (header != null) {
                header.forEach(post::setHeader);
            }
            if (body != null) {
                JSONObject jsonObject = new JSONObject(body);
                StringEntity postingString = new StringEntity(jsonObject.toString(), StandardCharsets.UTF_8);
                post.setEntity(postingString);
            }
            HttpResponse response = this.httpClient.execute(post);

            InputStream in = response.getEntity().getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            StringBuilder strber = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                strber.append(line);
            }
            br.close();
            in.close();
            result = strber.toString();
        } catch (Exception e) {
            logger.error("[String]Post error: {}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            post.abort();
        }
        return result;
    }

    /**
     * post 请求
     *
     * @param url
     * @param body
     * @param header
     * @return
     */
    public Map<String, Object> post2(String url, Map<String, Object> body, Map<String, String> header) {
        HttpPost post = new HttpPost(url);
        Map<String, Object> result = new HashMap<>();
        result.put(response, "");
        result.put(send, 0);
        result.put(received, 0);
        try {
//             设置超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(20 * 1000)
                    .setConnectTimeout(20 * 1000)
                    .setSocketTimeout(20 * 1000)
                    .build();
            post.setConfig(requestConfig);
            if (header != null) {
                // 这里计算header的字节
                header.forEach(post::setHeader);
            }
            if (body != null) {
                JSONObject jsonObject = new JSONObject(body);
                StringEntity postingString = new StringEntity(jsonObject.toString(), StandardCharsets.UTF_8);
                // 这里计算body的长度
                long bodyLen = postingString.getContentLength();
                result.put(send, (long) result.get(send) + bodyLen);
                post.setEntity(postingString);
            }
            HttpResponse response = this.httpClient.execute(post);
            InputStream in = response.getEntity().getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            StringBuilder strber = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                //strber.append(line).append('\n');
                strber.append(line);
            }
            br.close();
            in.close();
            result.put("result", strber.toString());
        } catch (Exception e) {
            logger.error("[String]Post error: {}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            post.abort();
        }
        return result;
    }

    /**
     * 向指定URL发送GET方法的请求, 无参数 或将参数带入url中
     *
     * @param url 发送请求的URL
     * @return 返回字节数组
     */
    public byte[] get(String url, Map<String, String> header) {
        byte[] result = null;
        HttpGet get = new HttpGet(url);
        try {
            if (header != null) {
                header.forEach(get::addHeader);
            }
            HttpResponse response = this.httpClient.execute(get);
            result = EntityUtils.toByteArray(response.getEntity());
        } catch (Exception e) {
            logger.error("[byte[]]Get error: {}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            get.abort();
        }
        return result;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url 发送请求的URL
     * @return 返回字符串
     */
    public String get(String url, Map<String, String> paramMap, Map<String, String> header) {
        String result = "";
        HttpGet get = new HttpGet(url);
        try {
            if (header != null) {
                header.forEach(get::addHeader);
            }
            if (paramMap != null) {
                List<NameValuePair> params = this.setHttpParams(paramMap);
                String param = URLEncodedUtils.format(params, StandardCharsets.UTF_8);
                get.setURI(URI.create(url + "?" + param));
            }
            HttpResponse response = this.httpClient.execute(get);
            result = this.getHttpEntityContent(response);
        } catch (Exception e) {
            logger.error("[String]Get error: {}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            get.abort();
        }
        return result;
    }

    public String put(String url, Map<String, Object> paramMap, Map<String, String> header) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        String result = "";

        CloseableHttpResponse httpResponse = null;
        try {
            if (header != null) {
                header.forEach(httpPut::setHeader);
            }
            if (paramMap != null) {
                JSONObject jsonObject = new JSONObject(paramMap);
                httpPut.setEntity(new StringEntity(jsonObject.toString(), StandardCharsets.UTF_8));
            }
            httpResponse = httpClient.execute(httpPut);
            result = getHttpEntityContent(httpResponse);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.getMessage());
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return result;
    }

    public String put(String url, String paramMap, Map<String, String> header) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        String result = "";

        CloseableHttpResponse httpResponse = null;
        try {
            if (header != null) {
                header.forEach(httpPut::setHeader);
            }
            if (paramMap != null) {
                httpPut.setEntity(new StringEntity(paramMap, StandardCharsets.UTF_8));
            }
            httpResponse = httpClient.execute(httpPut);
            result = getHttpEntityContent(httpResponse);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.getMessage());
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return result;
    }

    private List<NameValuePair> setHttpParams(Map<String, String> paramMap) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        Set<Map.Entry<String, String>> set = paramMap.entrySet();
        for (Map.Entry<String, String> entry : set) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return params;
    }

    private String getHttpEntityContent(HttpResponse response) throws UnsupportedOperationException, IOException {
        String result = "";
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream in = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
            br.close();
            in.close();
            result = stringBuilder.toString();
        }
        return result;
    }
}
