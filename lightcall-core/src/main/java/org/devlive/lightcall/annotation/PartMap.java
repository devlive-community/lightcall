package org.devlive.lightcall.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface PartMap
{
    String value();

    /**
     * 指定所有文件的 MIME 类型，如果为空则自动检测
     */
    String mimeType() default "application/octet-stream";

    /**
     * 是否必需
     */
    boolean required() default true;
}
