package org.bahmni.feed.crater;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bahmni.crater.web.CraterProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CraterAPIProperties implements CraterProperties {
    private static Logger logger = LogManager.getLogger(CraterAPIProperties.class);

    @Value("crater.host")
    private String craterHost;

    @Value("crater.port")
    private String craterPort;

    @Value("crater.token")
    private String craterToken;

    @Value("crater.connectionTimeoutInMilliseconds")
    private String craterConnectionTimeoutInMilliseconds;

    @Value("crater.replyTimeoutInMilliseconds")
    private String craterReplyTimeoutInMilliseconds;

    @Value("crater.maxFailedEvents")
    private String craterMaxFailedEvents;


    @Override
    public String getHost() {
        return craterHost;
    }

    @Override
    public int getPort() {
        return Integer.parseInt(craterPort);
    }

    @Override
    public String getToken() {
        return craterToken;
    }

    @Override
    public int getConnectionTimeoutInMilliseconds() {
        return Integer.parseInt(craterConnectionTimeoutInMilliseconds);
    }

    @Override
    public int getReplyTimeoutInMilliseconds() {
        return Integer.parseInt(craterReplyTimeoutInMilliseconds);
    }
}
