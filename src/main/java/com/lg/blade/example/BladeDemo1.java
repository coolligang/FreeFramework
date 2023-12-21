package com.lg.blade.example;

import com.hellokaton.blade.Blade;

/**
 * ProtocolFreeFramework
 * BladeJava
 * BladeDemo
 *
 * @author: ligang30
 * @date: 2022/6/17
 */

public class BladeDemo1 {

    public static void main(String[] args) {
        // demo 1
//        Blade.of().get("/", ctx -> ctx.text("Hello Blade")).start();

// demo 2  使用 RouteContext 获取
        Blade.create().get("/user", ctx -> {
            Integer age = ctx.queryInt("age");
            System.out.println("age is:" + age);
        }).start();
    }
}
