package org.devlive.lightcall.handler;

import org.devlive.lightcall.RequestContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ParameterHandlerFactory
{
    public static List<ParameterHandler> createHandlers(
            Method method,
            RequestContext context
    )
    {
        List<ParameterHandler> handlers = new ArrayList<>();
        handlers.add(RequestParamHandler.create(context.getUrlBuilder()));
        handlers.add(PathVariableHandler.create());
        handlers.add(HeaderHandler.create(context.getRequestBuilder(), method));
        return handlers;
    }
}
