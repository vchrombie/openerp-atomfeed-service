package org.bahmni.crater.web;

public interface CraterProperties {
    public String getHost();
    public int getPort();
    public String getToken();
    public int getConnectionTimeoutInMilliseconds();
    public int getReplyTimeoutInMilliseconds();
}
