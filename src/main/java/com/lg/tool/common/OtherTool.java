package com.lg.tool.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.time.Instant;
import java.util.*;

public class OtherTool {
    private static Logger logger = LogManager.getLogger(OtherTool.class);

    /**
     * 获取uuid
     *
     * @return
     */
    public synchronized static String getUUID() {
        return java.util.UUID.randomUUID().toString();
//        String time = Instant.now().toString().split("T")[1];
//        return id + time.replace(":", "").replace(".", "");
    }



    /**
     * 返回两个数的差
     *
     * @param num1
     * @param num2
     * @return
     */
    public static double getDifference(double num1, double num2) {
        return num1 > num2 ? num1 - num2 : num2 - num1;
    }

    /**
     * 返回绝对值
     *
     * @param num
     * @return
     */
    public static double getAbsoluteValue(double num) {
        return num > 0 ? num : -num;
    }

    /**
     * 将 json字符串转为 json
     *
     * @param str
     * @return
     */
    public static JSONObject stringToJsonObj(String str) {
        JSONObject jsonObject = null;
        try {
            jsonObject = JSON.parseObject(str);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return jsonObject;
    }

    public static Map stringToMap(String str) {
        // JSON类解析字符串
        return (Map) JSON.parse(str);
    }

    public static Map stringToMapObj(String str) {
        return JSON.parseObject(str);
    }

    public static String mapToString(Map map) {
        return JSON.toJSONString(map, SerializerFeature.WriteMapNullValue);
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("a", 123);
        map.put("b", "ccc");
        List<Object> list = new ArrayList<>();
        list.add(123);
        list.add("aaa");
        map.put("c", list);

        String objStr = mapToString(map);
        System.out.println(objStr);
        Map mapObj = stringToMap(objStr);
        System.out.println(mapObj);
        System.out.println(mapObj.get("a"));
    }
}


