package com.runbuddy.modules.ssh.model;

/**
 * Output from ssh session
 * Created by Administrator on 2017/5/8.
 */
public class SessionOutput extends HostSystem {
    String sessionId;
    StringBuilder output = new StringBuilder();

    public SessionOutput() {


    }
    public SessionOutput(String sessionId, HostSystem hostSystem) {
        this.sessionId=sessionId;
        this.setId(hostSystem.getId());
        this.setInstanceId(hostSystem.getInstanceId());
        this.setUser(hostSystem.getUser());
        this.setHost(hostSystem.getHost());
        this.setPort(hostSystem.getPort());
        this.setDisplayNm(hostSystem.getDisplayNm());
        this.setAuthorizedKeys(hostSystem.getAuthorizedKeys());

    }
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public StringBuilder getOutput() {
        return output;
    }

    public void setOutput(StringBuilder output) {
        this.output = output;
    }
}
