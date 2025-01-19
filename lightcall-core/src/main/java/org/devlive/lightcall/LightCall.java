package org.devlive.lightcall;

import org.devlive.lightcall.config.LightCallConfig;
import org.devlive.lightcall.proxy.LightCallProxy;

import java.lang.reflect.Proxy;

public class LightCall
{
    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> serviceClass, LightCallConfig config)
    {
        validateServiceInterface(serviceClass);

        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[] {serviceClass},
                new LightCallProxy(config)
        );
    }

    private static void validateServiceInterface(Class<?> service)
    {
        if (!service.isInterface()) {
            throw new IllegalArgumentException("API declarations must be interfaces.");
        }
        if (service.getInterfaces().length > 0) {
            throw new IllegalArgumentException("API interfaces must not extend other interfaces.");
        }
    }
}
