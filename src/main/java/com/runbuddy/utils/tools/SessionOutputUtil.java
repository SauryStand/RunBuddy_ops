package com.runbuddy.utils.tools;

import com.runbuddy.modules.ssh.model.SessionOutput;
import com.runbuddy.modules.ssh.model.UserSessionsOutput;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utility to is used to store the output for a session until the ajax call that brings it to the screen
 * Created by Administrator on 2017/5/8.
 */
public class SessionOutputUtil {
    private static Map<String, UserSessionsOutput> userSessionsOutputMap = new ConcurrentHashMap<String, UserSessionsOutput>();

    private SessionOutputUtil() {
    }

    /**
     * removes session for user session
     *
     * @param sessionId session id
     */
    public static void removeUserSession(String sessionId) {
        UserSessionsOutput userSessionsOutput = userSessionsOutputMap.get(sessionId);
        if (userSessionsOutput != null) {
            userSessionsOutput.getSessionOutputMap().clear();
        }
        userSessionsOutputMap.remove(sessionId);

    }

    /**
     * removes session output for host system
     *
     * @param sessionId  session id
     * @param instanceId id of host system instance
     */
    public static void removeOutput(String sessionId, Integer instanceId) {

        UserSessionsOutput userSessionsOutput = userSessionsOutputMap.get(sessionId);
        if (userSessionsOutput != null) {
            userSessionsOutput.getSessionOutputMap().remove(instanceId);
        }
    }

    /**
     * adds a new output
     *
     * @param sessionOutput session output object
     */
    public static void addOutput(SessionOutput sessionOutput) {

        UserSessionsOutput userSessionsOutput = userSessionsOutputMap.get(sessionOutput.getSessionId());
        if (userSessionsOutput == null) {
            userSessionsOutputMap.put(sessionOutput.getSessionId(), new UserSessionsOutput());
            userSessionsOutput = userSessionsOutputMap.get(sessionOutput.getSessionId());
        }
        userSessionsOutput.getSessionOutputMap().put(sessionOutput.getInstanceId(), sessionOutput);


    }


    /**
     * adds a new output
     *
     * @param sessionId  session id
     * @param instanceId id of host system instance
     * @param value      Array that is the source of characters
     * @param offset     The initial offset
     * @param count      The length
     */
    public static void addToOutput(String sessionId, Integer instanceId, char value[], int offset, int count) {

        UserSessionsOutput userSessionsOutput = userSessionsOutputMap.get(sessionId);
        if (userSessionsOutput != null) {
            userSessionsOutput.getSessionOutputMap().get(instanceId).getOutput().append(value, offset, count);
        }

    }


    /**
     * returns list of output lines
     *
     * @param sessionId session id object
     * @param user      user auth object
     * @return session output list
     */
    public static List<SessionOutput> getOutput(String sessionId) {
        List<SessionOutput> outputList = new ArrayList<SessionOutput>();
        UserSessionsOutput userSessionsOutput = userSessionsOutputMap.get(sessionId);
        if (userSessionsOutput != null) {
            for (Integer key : userSessionsOutput.getSessionOutputMap().keySet()) {
                //get output chars and set to output
                try {
                    SessionOutput sessionOutput = userSessionsOutput.getSessionOutputMap().get(key);
                    if (sessionOutput != null && sessionOutput.getOutput() != null
                            && StringUtils.isNotEmpty(sessionOutput.getOutput())) {
                        outputList.add(sessionOutput);
                        userSessionsOutput.getSessionOutputMap().put(key, new SessionOutput(sessionId, sessionOutput));
                    }
                } catch (Exception ex) {
                }

            }

        }

        return outputList;
    }


}
