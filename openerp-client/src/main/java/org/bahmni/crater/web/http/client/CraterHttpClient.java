package org.bahmni.crater.web.http.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CraterHttpClient {

    private static final Logger logger = LogManager.getLogger(CraterHttpClient.class);
    private RestTemplate restTemplate;

    private boolean isTimeoutSet;

    @Autowired
    public CraterHttpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String post(String url, String requestBody, String token) {
        try {
            logger.debug("Post Data: {}", requestBody);
            HttpEntity<String> stringHttpEntity = new HttpEntity<String>(requestBody,
                    getHttpHeaders(token));
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
                    stringHttpEntity, String.class);
            String response = responseEntity != null ? responseEntity.getBody() : "";
            logger.debug("Post Data output: {}", response);
            return response;
        } catch (Exception e) {
            logger.error("Could not post  to {}", url, e);
            logger.error("Post data: {}", requestBody);
            throw new RuntimeException("Could not post message", e);
        }
    }

    // TODO move it to common client
    private HttpHeaders getHttpHeaders(String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("content-type", "application/json; charset=UTF-8");
        httpHeaders.set("accept", "application/json");
        httpHeaders.set("authorization", "Bearer " + token);
        return httpHeaders;
    }

    public void setTimeout(int replyTimeoutInMilliseconds) {
        if (!isTimeoutSet) {
            try {
                HttpComponentsClientHttpRequestFactory requestFactoryWithTimeout = new HttpComponentsClientHttpRequestFactory();
                requestFactoryWithTimeout.setReadTimeout(replyTimeoutInMilliseconds);
                restTemplate.setRequestFactory(requestFactoryWithTimeout);

                isTimeoutSet = true;
            } catch (Throwable e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
