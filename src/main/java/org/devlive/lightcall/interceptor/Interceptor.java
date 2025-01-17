package org.devlive.lightcall.interceptor;

import okhttp3.Request;
import okhttp3.Response;

public interface Interceptor
{
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
