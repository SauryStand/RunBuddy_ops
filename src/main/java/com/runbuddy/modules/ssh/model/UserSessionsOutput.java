package com.runbuddy.modules.ssh.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2017/5/8.
 */
public class UserSessionsOutput {
    //instance id, host output
    Map<Integer, SessionOutput> sessionOutputMap = new ConcurrentHashMap<Integer, SessionOutput>();

    public Map<Integer, SessionOutput> getSessionOutputMap() {
        return sessionOutputMap;
    }

    public void setSessionOutputMap(Map<Integer, SessionOutput> sessionOutputMap) {
        this.sessionOutputMap = sessionOutputMap;
    }
}
