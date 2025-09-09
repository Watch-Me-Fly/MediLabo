package com.oc.reportservice.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class RestTemplateConfig {

    @Autowired
    private RestTemplateBuilder builder;

    @Bean
    public RestTemplate restTemplate() {
        return builder
                .interceptors((request,
                               body,
                               execution) -> {
                    ServletRequestAttributes attributes =
                            (ServletRequestAttributes) RequestContextHolder
                                    .getRequestAttributes();
                    if (attributes != null) {
                        HttpServletRequest servletRequest = attributes.getRequest();
                        String cookie = servletRequest.getHeader("Cookie");
                        if (cookie != null) {
                            request.getHeaders().add("Cookie", cookie);
                        }
                    }
                    return execution.execute(request, body);
                })
                .build();
    }

    @Bean
    @ConfigurationProperties(prefix = "app.endpoints")
    public EndpointsProperties endpointsProperties() {
        return new EndpointsProperties();
    }

}
