package com.lg.framework.impl.function;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ResearchQADescription {
    //测试名
    String name() default "";

    // 测试描述
    String[] step() default {};
}
