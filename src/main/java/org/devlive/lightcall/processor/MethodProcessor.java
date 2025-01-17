package org.devlive.lightcall.processor;

import org.devlive.lightcall.RequestContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public interface MethodProcessor<A extends Annotation>
{
    Class<A> getAnnotationType();

    Object process(Object proxy, Method method, Object[] args, A annotation, RequestContext context)
            throws Throwable;
}
