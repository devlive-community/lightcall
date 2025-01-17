package org.devlive.lightcall.config;

import lombok.Data;
import org.devlive.lightcall.interceptor.Interceptor;

import java.util.ArrayList;
import java.util.List;

@Data
public class LightCallConfig
{
    private String baseUrl;
    private int connectTimeout;
    private int readTimeout;
    private List<Interceptor> interceptors = new ArrayList<>();

    private LightCallConfig(String baseUrl)
    {
        this.baseUrl = baseUrl;
    }

    public static LightCallConfig create(String baseUrl)
    {
        return new LightCallConfig(baseUrl)
                .connectTimeout(5000)
                .readTimeout(5000);
    }

    public static LightCallConfig create(String baseUrl, int connectTimeout, int readTimeout)
    {
        return LightCallConfig.create(baseUrl)
                .connectTimeout(connectTimeout)
                .readTimeout(readTimeout);
    }

    public LightCallConfig connectTimeout(int connectTimeout)
    {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public LightCallConfig readTimeout(int readTimeout)
    {
        this.readTimeout = readTimeout;
        return this;
    }

    public LightCallConfig addInterceptor(Interceptor interceptor)
    {
        this.interceptors.add(interceptor);
        return this;
    }
}
