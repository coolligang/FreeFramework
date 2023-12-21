package com.lg.blade.example;

import com.hellokaton.blade.Blade;
import com.lg.blade.bean.TemplateConfig;
import com.lg.blade.controller.IndexController;

/**
 * ProtocolFreeFramework
 * BladeDemo3
 * Application
 *
 * @author: ligang30
 * @date: 2022/6/17
 */

public class BladeDemo3 {
    public static void main(String[] args) {
        // 1. 指定端口 8080
        // 2. 添加路由控制器
        // 3. Start blade
        Blade.create().listen(8080).start(IndexController.class, args);

        // -------------  以下代码执行不到，上面已经阻塞
        // 创建一个Blade
        Blade blade=Blade.create();
        // 添加 BladeLoader
        blade.addLoader(new TemplateConfig());
    }
}
