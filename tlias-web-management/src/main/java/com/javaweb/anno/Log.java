package com.javaweb.anno;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 指定生效策略,运行时生效
@Target(ElementType.METHOD) //指定生效类型，在方法上生效
public @interface Log {
}
