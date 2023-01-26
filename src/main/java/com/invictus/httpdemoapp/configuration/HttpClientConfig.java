package com.invictus.httpdemoapp.configuration;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {

    @Value("${http.proxy.host}")
    String httpProxyHost;
    @Value("${http.proxy.port}")
    int httpProxyPort;
    @Value("${http.proxy.username}")
    String httpProxyUsername;
    @Value("${http.proxy.password}")
    String httpProxyPassword;

    @Bean
    public HttpClient getHttpClientWithProxy() {
        HttpClientBuilder builder = HttpClientBuilder.create();
        HttpHost proxy = new HttpHost(httpProxyHost, httpProxyPort);
        DefaultProxyRoutePlanner planner = new DefaultProxyRoutePlanner(proxy);

        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope(proxy),
                new UsernamePasswordCredentials(httpProxyUsername, httpProxyPassword)
        );
        builder.setRoutePlanner(planner);
        builder.setDefaultCredentialsProvider(credentialsProvider);

        return builder.build();
    }

    @Bean
    public HttpClient getHttpClientWithNoProxy() {
        HttpClientBuilder builder = HttpClientBuilder.create();
        return builder.build();
    }
}
