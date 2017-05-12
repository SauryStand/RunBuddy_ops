package com.runbuddy.modules.ssh.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2017/5/5.
 */
public class UserSchSessions {

    Map<Integer, SchSession> schSessionMap = new ConcurrentHashMap<Integer, SchSession>();

    public Map<Integer,SchSession> getSchSessionMap(){
        return schSessionMap;
    }

    public void setSchSessionMap(Map<Integer, SchSession> schSessionMap) {

        this.schSessionMap = schSessionMap;
    }


}
