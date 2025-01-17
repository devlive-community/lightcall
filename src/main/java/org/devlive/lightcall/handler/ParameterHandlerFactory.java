package org.devlive.lightcall.handler;

import okhttp3.HttpUrl;

import java.util.ArrayList;
import java.util.List;

public class ParameterHandlerFactory
{
    public static List<ParameterHandler> createHandlers(HttpUrl.Builder urlBuilder)
    {
        List<ParameterHandler> handlers = new ArrayList<>();
        handlers.add(RequestParamHandler.create(urlBuilder));
        handlers.add(PathVariableHandler.create());
        return handlers;
    }
}
