package com.runbuddy.utils.tools;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;
import com.runbuddy.modules.ssh.web.SSHCommander;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Utility to obtain the authentication token from the http session and the user id from the auth token
 * Created by Administrator on 2017/5/8.
 */
public class AuthUtil {

    public static final String SESSION_ID = "sessionId";
    public static final String USER_ID = "userId";
    public static final String AUTH_TOKEN = "authToken";
    public static final String TIMEOUT = "timeout";

    private AuthUtil() {
    }

    /**
     * query session for timeout
     *
     * @param session http session
     * @return timeout string
     */
    public static String getTimeout(HttpSession session) {
        String timeout = (String) session.getAttribute(TIMEOUT);
        return timeout;
    }

    /**
     * set session timeout
     *
     * @param session http session
     */
    public static void setTimeout(HttpSession session) {
        //set session timeout
        SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyyHHmmss");
        Calendar timeout = Calendar.getInstance();
        timeout.add(Calendar.MINUTE, 15);
        session.setAttribute(TIMEOUT, sdf.format(timeout.getTime()));
    }

    /**
     * delete all session information
     *
     * @param session
     */
    public static void deleteAllSession(HttpSession session) {

        session.setAttribute(TIMEOUT, null);
        session.setAttribute(AUTH_TOKEN, null);
        session.setAttribute(USER_ID, null);
        session.setAttribute(SESSION_ID,null);

        session.invalidate();
    }

    /**
     * 格式化文件大小显示
     * @param size
     * @return
     */
    public static String prettyFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }


    /**
     * 取得指定路径的真实路径（如：/home/bp/.. 将返回/home）
     * @param session
     * @param _path
     * @return
     */
    public static String getRealPath(Session session, String _path) {
        String path = null;
        try {
            ChannelExec channel = (ChannelExec)session.openChannel("exec");
            if(_path != null && !_path.equals("")){
                channel.setCommand("cd "+_path+";pwd");
            }else{
                channel.setCommand("pwd");
            }

            channel.setErrStream(System.err);
            channel.setInputStream(null);
            InputStream in = channel.getInputStream();
            InputStreamReader is = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(is);
            channel.connect(SSHCommander.CHANNEL_TIMEOUT);//time out handle
            String _read= null;
            String lastRead = null;
            while ((_read = reader.readLine()) != null) {
                lastRead = _read;
            }
            path =  lastRead;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return path;
    }



}
