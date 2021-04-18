package com.beata.sync.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration(proxyBeanMethods = false)
public class BeataRestTemplateAutoConfiguration {


    @Bean
    public BeataRestTemplateInterceptor seataRestTemplateInterceptor() {
        return new BeataRestTemplateInterceptor();
    }

    @Autowired(required = false)
    private Collection<RestTemplate> restTemplates;

    @Autowired
    private BeataRestTemplateInterceptor beataRestTemplateInterceptor;

    @PostConstruct
    public void init() {
        if (this.restTemplates != null) {
            for (RestTemplate restTemplate : restTemplates) {
                List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>(
                        restTemplate.getInterceptors());
                interceptors.add(this.beataRestTemplateInterceptor);
                restTemplate.setInterceptors(interceptors);
            }
        }
    }
}
