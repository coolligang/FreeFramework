package com.lg.example.chart;

import com.carrotsearch.sizeof.RamUsageEstimator;
import com.lg.tool.httpClient.HttpHelper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * ProtocolFreeFramework
 * ByteCount
 * 统计对象的字节数
 *
 * @author: ligang30
 * @date: 2022/11/29
 */

public class SizeOfUtil {
    public static enum SizeEnum {
        B {
            @Override
            public double getFromByteSize(long byteSize) {
                return byteSize;
            }
        }, K {
            @Override
            public double getFromByteSize(long byteSize) {
                return byteSize * 1.0 / 1024;
            }
        }, M {
            @Override
            public double getFromByteSize(long byteSize) {
                return byteSize * 1.0 / 1024 / 1024;
            }
        }, G {
            @Override
            public double getFromByteSize(long byteSize) {
                return byteSize * 1.0 / 1024 / 1024 / 1024;
            }
        };

        public abstract double getFromByteSize(long byteSize);
    }

    /**
     * 默认
     * 返回bytes
     *
     * @param obj
     * @return
     */
    public static double getObjectSize(Object obj, SizeEnum sizeEnum) {
        long byteSize = 0;
        if (obj instanceof Collection<?>) {
            byteSize = RamUsageEstimator.sizeOfAll(obj);
        } else {
            byteSize = RamUsageEstimator.sizeOf(obj);
        }
        return sizeEnum.getFromByteSize(byteSize);
    }

    public static void main(String[] args) {
        Map<String, Object> testMap = new HashMap<>();
        testMap.put("a", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        testMap.put("b", 12342);
        testMap.put("c", 1.234);
        testMap.put("key", "你好啊");
        System.out.println(SizeOfUtil.getObjectSize(testMap, SizeOfUtil.SizeEnum.B));
        Double bodyL = SizeOfUtil.getObjectSize(testMap.toString(), SizeOfUtil.SizeEnum.B);
//        System.out.println(SizeOfUtil.getObjectSize("你好你好", SizeOfUtil.SizeEnum.B));
//        System.out.println(SizeOfUtil.getObjectSize("412341231232", SizeOfUtil.SizeEnum.B));

        HttpHelper httpHelper = new HttpHelper();
        System.out.println(SizeOfUtil.getObjectSize(HttpHelper.getDefaultHeader(), SizeEnum.B));
        Double headerL = SizeOfUtil.getObjectSize(HttpHelper.getDefaultHeader().toString(), SizeEnum.B);
        Double size = headerL + bodyL;
        System.out.println("headerSize= " + headerL);
        System.out.println("bodySize= " + bodyL);
        System.out.println("length= " + size);
        String url = "http://127.0.0.1:9005/body";
        String res = httpHelper.post(url, testMap, HttpHelper.getDefaultHeader());
        System.out.println(res);
        System.out.println(SizeOfUtil.getObjectSize(res, SizeEnum.B));
    }
}

