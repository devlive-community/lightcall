package org.devlive.lightcall.config;

import lombok.Data;

@Data
public class LightCallConfig
{
    private String baseUrl;
    private int connectTimeout;
    private int readTimeout;

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
}
