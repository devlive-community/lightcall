package org.devlive.lightcall.handler;

import org.devlive.lightcall.RequestContext;

import java.util.ArrayList;
import java.util.List;

public class ParameterHandlerFactory
{
    public static List<ParameterHandler> createHandlers(RequestContext context)
    {
        List<ParameterHandler> handlers = new ArrayList<>();
        handlers.add(RequestParamHandler.create(context.getUrlBuilder()));
        handlers.add(PathVariableHandler.create());
        handlers.add(HeaderHandler.create(context.getRequestBuilder()));
        return handlers;
    }
}
