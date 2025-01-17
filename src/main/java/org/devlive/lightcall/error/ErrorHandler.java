package org.devlive.lightcall.error;

import okhttp3.Request;
import okhttp3.Response;

public interface ErrorHandler
{
    /**
     * 错误处理器的执行顺序，数字越小优先级越高
     */
    int order();

    /**
     * 检查是否可以处理该错误
     */
    boolean canHandle(Request request, Response response, Exception exception);

    /**
     * 处理错误
     *
     * @return 如果返回 null，表示错误已被处理，否则继续抛出异常
     */
    Object handle(Request request, Response response, Exception exception, Class<?> returnType)
            throws Exception;
}
