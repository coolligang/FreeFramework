package com.lg.blade.bean;

import com.hellokaton.blade.ioc.annotation.Bean;

/**
 * ProtocolFreeFramework
 * UserService
 * 自定义组件
 * 很多时候我们有一些自己的业务，我们需要自定义实现一些 Service 这样的服务。
 * 在 Blade 中将概念简化了，认为一切组件都是一个 Bean。 那么我可以这样创建一个组件：
 * 在控制器中去使用组件，见 IndexController中的使用Demo
 * 使用 @Inject 注解注入一个我们定义好的组件，当然组件和组件的调用也是如此。
 * <p>
 * =========== 内置组件 ===========
 * 为了让开发者花更少的时候在开发核心业务上面，Blade 内置了一些 组件 供你使用。
 * DefaultExceptionHandler：异常处理器，你如果有自定义的异常处理可以继承它。
 * BasicAuthMiddleware：Basic认证，如果用的到的话可以直接使用。
 * CsrfMiddleware：帮助你验证 CSRF Token
 *
 * @author: ligang30
 * @date: 2022/6/17
 */

@Bean
public class UserService {
    public String sayHi() {
        return "Hi blade";
    }
}
