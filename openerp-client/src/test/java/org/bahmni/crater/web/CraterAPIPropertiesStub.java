package org.bahmni.crater.web;

public class CraterAPIPropertiesStub implements CraterProperties {

    @Override
    public String getHost() {
        return "localhost";
    }

    @Override
    public int getPort() {
        return 8080;
    }

    @Override
    public String getToken() {
        return "";
    }

    @Override
    public int getConnectionTimeoutInMilliseconds() {
        return 0;
    }

    @Override
    public int getReplyTimeoutInMilliseconds() {
        return 0;
    }
}
