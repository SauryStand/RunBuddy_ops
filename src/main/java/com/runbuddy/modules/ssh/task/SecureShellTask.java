package com.runbuddy.modules.ssh.task;

import com.runbuddy.modules.ssh.model.SessionOutput;
import com.runbuddy.utils.tools.SessionOutputUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Task to watch for output read from the ssh session stream
 * Created by Administrator on 2017/5/8.
 */
public class SecureShellTask implements Runnable {

    InputStream outFromChannel;
    SessionOutput sessionOutput;

    public SecureShellTask(SessionOutput sessionOutput, InputStream outFromChannel) {

        this.sessionOutput = sessionOutput;
        this.outFromChannel = outFromChannel;
    }


    //@Override
    public void run() {
        InputStreamReader isr = new InputStreamReader(outFromChannel);
        BufferedReader br = new BufferedReader(isr);
        try {

            SessionOutputUtil.addOutput(sessionOutput);

            char[] buff = new char[1024];
            int read;
            while ((read = br.read(buff)) != -1) {

                SessionOutputUtil.addToOutput(sessionOutput.getSessionId(), sessionOutput.getInstanceId(), buff, 0, read);
                Thread.sleep(50);
            }

            SessionOutputUtil.removeOutput(sessionOutput.getSessionId(), sessionOutput.getInstanceId());

        } catch (Exception ex) {
        }
    }
}
