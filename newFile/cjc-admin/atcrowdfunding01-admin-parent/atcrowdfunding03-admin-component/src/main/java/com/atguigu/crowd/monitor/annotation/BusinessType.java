package com.atguigu.crowd.monitor.annotation;

import java.lang.annotation.*;

/**
 * 用于标记业务类型,也用于标记是否被日志拦截器拦截
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BusinessType {
    String value();
}
