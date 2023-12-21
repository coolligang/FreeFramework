package com.lg.blade.example;

import com.hellokaton.blade.Blade;
import com.lg.blade.controller.IndexController;

/**
 * ProtocolFreeFramework
 * BladeDemo2
 * demo2
 *
 * @author: ligang30
 * @date: 2022/6/17
 */

public class BladeDemo2 {
    public static void main(String[] args) {
        Blade blade = Blade.create();
        // Create a route: /user/:uid
        blade.get("/user/:uid", ctx -> {
            Integer uid = ctx.pathInt("uid");
            ctx.text("uid : " + uid);
        });

        // Create two parameters route
        blade.get("/users/:uid/post/:pid", ctx -> {
            String uid = ctx.pathString("uid");
            Integer pid = ctx.pathInt("pid");
            String msg = "uid = " + uid + ", pid = " + pid;
            ctx.text(msg);
        });
        // Start blade
        blade.start();
    }
}
