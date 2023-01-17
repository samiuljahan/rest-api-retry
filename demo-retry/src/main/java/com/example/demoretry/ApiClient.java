package com.example.demoretry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ApiClient {

    private final RetryTemplate retryTemplate;

    private final RestTemplate restTemplate;

    private static String URL_SUCCESS = "http://localhost:8080/example";
    private static String URL_ERROR = "http://localhost:8080/example?error=403";
    private static String URL = URL_ERROR;

    public ApiClient(RetryTemplate retryTemplate, RestTemplate restTemplate) {
        this.retryTemplate = retryTemplate;
        this.restTemplate = restTemplate;
    }


    public void callApi() {
        retryTemplate.execute(retryContext -> {
            try {
                // Make the API call
                if (retryContext.getRetryCount() > 1) {
                    URL = URL_SUCCESS;
                }
                ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);
                // Log the response
                log.info("API call successful. Response: {}", response.getBody());
            } catch (Exception ex) {
                // Log the exception and retry count
                log.error("API call failed with Internal Server Error. Retrying... Retry Count: {}", retryContext.getRetryCount(), ex);
                throw ex;
            }
            return null;
        });
    }





}
