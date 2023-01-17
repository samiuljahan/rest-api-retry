package com.example.demoretry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.ExceptionClassifierRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Config {

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        //set retry policy
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);
        retryTemplate.setRetryPolicy(retryPolicy);

        //set backoff policy
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(1); // 5 seconds
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);


        // configure exception classes to retry on
        ExceptionClassifierRetryPolicy exceptionClassifierRetryPolicy = new ExceptionClassifierRetryPolicy();
        Map<Class<? extends Throwable>, RetryPolicy> policyMap = new HashMap<>();
        //policyMap.put(HttpServerErrorException.InternalServerError.class, retryPolicy);
        policyMap.put(HttpServerErrorException.class, retryPolicy);
        policyMap.put(HttpClientErrorException.BadRequest.class, retryPolicy);
        exceptionClassifierRetryPolicy.setPolicyMap(policyMap);
        retryTemplate.setRetryPolicy(exceptionClassifierRetryPolicy);

        return retryTemplate;
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
