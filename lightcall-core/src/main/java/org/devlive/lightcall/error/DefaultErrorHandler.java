package org.devlive.lightcall.error;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;
import org.devlive.lightcall.RequestException;

@Slf4j
public class DefaultErrorHandler
        implements ErrorHandler
{
    @Override
    public int order()
    {
        return Integer.MAX_VALUE; // 默认最低优先级
    }

    @Override
    public boolean canHandle(Request request, Response response, Exception exception)
    {
        return true; // 默认处理所有错误
    }

    @Override
    public Object handle(Request request, Response response, Exception exception, Class<?> returnType)
    {
        if (exception != null) {
            throw new RequestException("Request failed: " + exception.getMessage(), exception);
        }

        if (response != null && !response.isSuccessful()) {
            throw new RequestException("Request failed with code: " + response.code());
        }

        throw new RequestException("Unknown error occurred");
    }
}
