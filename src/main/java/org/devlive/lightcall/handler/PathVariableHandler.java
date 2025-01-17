package org.devlive.lightcall.handler;

import lombok.extern.slf4j.Slf4j;
import org.devlive.lightcall.annotation.PathVariable;

import java.lang.reflect.Parameter;

@Slf4j
public class PathVariableHandler
        implements ParameterHandler
{
    private PathVariableHandler() {}

    public static PathVariableHandler create()
    {
        return new PathVariableHandler();
    }

    @Override
    public boolean canHandle(Parameter parameter)
    {
        return parameter.isAnnotationPresent(PathVariable.class);
    }

    @Override
    public String handle(Parameter parameter, Object arg, String path)
    {
        if (arg == null) {
            if (parameter.getAnnotation(PathVariable.class).required()) {
                throw new IllegalArgumentException(
                        String.format("Path variable '%s' is required but was null", parameter.getName()));
            }
            return path;
        }

        PathVariable annotation = parameter.getAnnotation(PathVariable.class);
        String paramName = annotation.value().isEmpty() ? parameter.getName() : annotation.value();
        String placeholder = "{" + paramName + "}";

        if (!path.contains(placeholder)) {
            log.warn("Path variable placeholder {} not found in path: {}", placeholder, path);
            return path;
        }

        log.debug("Replacing path variable {} with value: {}", paramName, arg);
        return path.replace(placeholder, String.valueOf(arg));
    }
}
