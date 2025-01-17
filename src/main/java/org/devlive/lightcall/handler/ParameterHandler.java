package org.devlive.lightcall.handler;

import java.lang.reflect.Parameter;

public interface ParameterHandler
{
    /**
     * 检查是否可以处理该参数
     */
    boolean canHandle(Parameter parameter);

    /**
     * 处理参数
     *
     * @param parameter 方法参数
     * @param arg 参数值
     * @param path 请求路径
     * @return 处理后的路径
     */
    String handle(Parameter parameter, Object arg, String path);
}
