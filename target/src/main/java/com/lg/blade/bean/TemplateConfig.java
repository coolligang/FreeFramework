package com.lg.blade.bean;

import com.hellokaton.blade.Blade;
import com.hellokaton.blade.ioc.annotation.Bean;
import com.hellokaton.blade.ioc.annotation.Order;
import com.hellokaton.blade.loader.BladeLoader;

/**
 * ProtocolFreeFramework
 * TemplateConfig
 * 在一个 Web 开发中我们经常会使用到项目启动的时候加载一些 配置 或者干一些其他的事情，
 * 在Blade中设计了一个接口 BladeLoader，你只需要创建一个自己的类实现这个接口即可完成初始化加载的动作。
 * 比如我要在项目启动的时候设置模板引擎
 *
 * @author: ligang30
 * @date: 2022/6/17
 */

@Order(1)
// 我有多个组件，我希望他们是按顺序执行的，所以又添加了一个 @Order 注解， 同时指定了这个组件的顺序是 1，
// 那么框架在启动的时候会按照顺序执行所有组件的 load 方法。
@Bean  // 告诉框架这是一个组件，你将它帮我托管在 IOC 容器里吧
public class TemplateConfig implements BladeLoader {
    @Override
    public void load(Blade blade) {
        System.out.println("Loader ======================================");
//        JetbrickTemplateEngine templateEngine = new JetbrickTemplateEngine();
//        blade.templateEngine(templateEngine);
    }
}
