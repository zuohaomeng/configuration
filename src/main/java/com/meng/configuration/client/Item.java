package com.meng.configuration.client;

import java.lang.annotation.*;

/**
 * 配置项的注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Item {
    String key() default "";
}
