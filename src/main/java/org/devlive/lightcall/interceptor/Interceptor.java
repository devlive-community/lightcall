package org.devlive.lightcall.interceptor;

import okhttp3.Request;
import okhttp3.Response;

public interface Interceptor
{
    /**
     * 定义优先级，数字越小优先级越高
     *
     * @return 优先级
     */
    default int order()
    {
        return 0;
    }

    /**
     * 在请求发送前调用
     *
     * @param request 原始请求
     * @return 处理后的请求
     */
    Request beforeRequest(Request request);

    /**
     * 在收到响应后调用
     *
     * @param response 原始响应
     * @return 处理后的响应
     */
    Response afterResponse(Response response)
            throws Exception;
}
