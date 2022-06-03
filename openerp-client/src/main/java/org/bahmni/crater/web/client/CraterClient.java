package org.bahmni.crater.web.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bahmni.crater.web.CraterProperties;
import org.bahmni.crater.web.http.client.CraterHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy
public class CraterClient {
    private static final Logger logger = LogManager.getLogger(CraterClient.class);

    private final int connectionTimeoutInMilliseconds;
    private final int replyTimeoutInMilliseconds;
    private String host;
    private int port;
    private String token;
    private CraterHttpClient httpClient;

    private String craterEndPoint = "/api/v1/customers";

    @Autowired
    public CraterClient(CraterHttpClient httpClient, CraterProperties properties) {
        this.connectionTimeoutInMilliseconds = properties.getConnectionTimeoutInMilliseconds();
        this.replyTimeoutInMilliseconds = properties.getReplyTimeoutInMilliseconds();
        this.host = properties.getHost();
        this.port = properties.getPort();
        this.token = properties.getToken();
        this.httpClient = httpClient;
    }

    public int createCraterCustomer(String body) throws JsonProcessingException {
        CraterHttpClient client = httpClient();
        String result = client.post("http://" + host + ":" + port + craterEndPoint, body, token);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResult = objectMapper.readTree(result);
        int customerId = Integer.parseInt(jsonResult.get("data").get("id").asText());
        logger.info("Created customer with id {} in crater for request {}", customerId, body);
        return customerId;
    }

    private CraterHttpClient httpClient() {
        httpClient.setTimeout(replyTimeoutInMilliseconds);
        return httpClient;
    }
}
