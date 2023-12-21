package com.lg.tool.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class ChoiceFunc<T> {
    private static Logger logger = LogManager.getLogger(ChoiceFunc.class);

    /**
     * 随机返回对象
     *
     * @return
     */
    public T getObjRandom(List<T> listObj) {
        Random random = new Random();
        return listObj.get(random.nextInt(listObj.size()));
    }

    /**
     * 按权重返回对象
     *
     * @param mapObj<权重对象:T 权重:Integer>
     * @return
     */
    public T getObjByWeight(Map<T, Integer> mapObj) {
        int sum = 0;
        for (Map.Entry<T, Integer> entry : mapObj.entrySet()) {
            if (entry.getValue() <= 0) {
                // 将权重非正的对象排除
                mapObj.remove(entry.getKey());
            } else {
                sum += entry.getValue();
            }
        }
        int offset = new Random().nextInt(sum);
        // int offset = new Random().nextInt(mapObj.values().stream().mapToInt(i -> i).sum());
        T target = null;
        for (Map.Entry<T, Integer> entry : mapObj.entrySet()) {
            offset -= entry.getValue();
            if (offset < 0) {
                target = entry.getKey();
                break;
            }
        }
        return target;
    }

    public static int getRandomNumber(int min, int max) {
        //int s = random.nextInt(max)%(max-min+1) + min;
        return new Random().nextInt(max - min) + min;
    }

    /**
     * 获取len个长度的随机字符，因为经过len次随机，
     * 故在高并发下，可以避免UUID重复的情况
     *
     * @param len
     * @return String
     */
    public static String getRandomString(int len) {
        char[] selectChar = new char[len];
        for (int i = 0; i < len; i++) {
            int index = getRandomNumber(1, 3);
            int select = -1;
            switch (index) {
                case 1:
                    // 数字
                    select = getRandomNumber(48, 57);
                    break;
                case 2:
                    // 小写字母
                    select = getRandomNumber(97, 122);
                    break;
                case 3:
                    // 大写字母
                    select = getRandomNumber(65, 90);
                    break;
                default:
                    break;
            }
            selectChar[i] = (char) select;
        }
        return String.valueOf(selectChar);
    }

    public static void main(String[] args) {
//        ChoiceFunc<String> mathFunc = new ChoiceFunc<>();
//        Map<String, Integer> map = new HashMap<>();
//        map.put("a", 1);
//        map.put("b", 1);
//        map.put("c", 2);
//        map.put("d", 4);
//        map.put("e", 0);
//
//        Map<String, Integer> rs = new HashMap<>();
//        map.forEach((key, weight) -> {
//            rs.put(key, 0);
//        });
//        for (int i = 0; i < 100000; i++) {
//            String key = mathFunc.getObjByWeight(map);
//            Integer times = rs.get(key);
//            rs.put(key, times + 1);
//        }
//        rs.forEach((key, times) -> {
//            System.out.println(key + " " + times);
//        });
        System.out.println(getRandomString(32));
    }
}
