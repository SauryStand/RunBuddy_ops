package com.runbuddy.modules.ssh.web;

import com.jcraft.jsch.*;
import com.runbuddy.modules.ssh.model.*;
import com.runbuddy.modules.ssh.task.SecureShellTask;
import com.runbuddy.utils.tools.AuthUtil;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright 2013 Sean Kavanagh - sean.p.kavanagh6@gmail.com
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class SSHCommander {

    private static Logger log = Logger.getLogger(SSHCommander.class);
    public static final int SERVER_ALIVE_INTERVAL =  60 * 1000;
    public static final int SESSION_TIMEOUT = 60000;
    public static final int CHANNEL_TIMEOUT = 60000;
    public static final  boolean agentForwarding = false;
    private static Map<String, UserSchSessions> userSchSessionMap = new ConcurrentHashMap<String, UserSchSessions>();

    private SSHCommander() {
    }


    public static Map<String, UserSchSessions> getUserSchSessionMap() {
        return userSchSessionMap;
    }

    /**
     * 打开一个终端
     * @param request
     * @param hostSystems
     * @return
     */
    public static String openSSHTermOnSystem(HttpServletRequest request, List<HostSystem> hostSystems) {
        String sessionId = UUID.randomUUID().toString();
        if(request.getSession().getAttribute("userThemeSettings") == null){
            request.getSession().setAttribute("userThemeSettings", new UserSettings());
        }
        request.getSession().setAttribute(AuthUtil.SESSION_ID, sessionId);
        request.setAttribute("hosts", hostSystems);
        cloneSSHTermOnSystem(sessionId,hostSystems);
        return "sshClient/secure_shell";
    }

    /**
     * 打开一个终端，并执行一个脚本
     * @param request
     * @param hostSystems
     * @param script
     * @return
     */
    public static String openSSHTermOnSystem(HttpServletRequest request,List<HostSystem> hostSystems,String script) {
        String sessionId = UUID.randomUUID().toString();
        if(request.getSession().getAttribute("userThemeSettings") == null){
            request.getSession().setAttribute("userThemeSettings", new UserSettings());
        }
        request.getSession().setAttribute(AuthUtil.SESSION_ID, sessionId);
        request.setAttribute("script", script);
        request.setAttribute("hosts", hostSystems);
        cloneSSHTermOnSystem(sessionId,hostSystems);
        return "sshClient/secure_shell";
    }

    /**
     * 复制会话
     * @param sessionId
     * @param hostSystems
     * @return
     */
    protected static List<HostSystem> cloneSSHTermOnSystem(String sessionId ,List<HostSystem> hostSystems) {
        for(HostSystem hostSystem : hostSystems ){
            JSch jsch = new JSch();
            SchSession schSession = null;

            int instanceId = getNextInstanceId(sessionId, userSchSessionMap);
            hostSystem.setStatusCd(HostSystem.SUCCESS_STATUS);
            hostSystem.setInstanceId(instanceId);
            try {
                //create session
                Session session = jsch.getSession(hostSystem.getUser(), hostSystem.getHost(), hostSystem.getPort() == null ? 22 : hostSystem.getPort());
                session.setPassword(hostSystem.getPassword());
                session.setConfig("StrictHostKeyChecking", "no");
                session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
                session.setServerAliveInterval(SERVER_ALIVE_INTERVAL);
                session.connect(SESSION_TIMEOUT);
                Channel channel = session.openChannel("shell");
                if (agentForwarding) {
                    ((ChannelShell) channel).setAgentForwarding(true);
                }
                ((ChannelShell) channel).setPtyType("xterm");

                InputStream outFromChannel = channel.getInputStream();

                //new session output
                SessionOutput sessionOutput = new SessionOutput(sessionId, hostSystem);

                Runnable run = new SecureShellTask(sessionOutput, outFromChannel);
                Thread thread = new Thread(run);
                thread.start();


                OutputStream inputToChannel = channel.getOutputStream();
                PrintStream commander = new PrintStream(inputToChannel, true);


                channel.connect();

                schSession = new SchSession();
                schSession.setSession(session);
                schSession.setChannel(channel);
                schSession.setCommander(commander);
                schSession.setInputToChannel(inputToChannel);
                schSession.setOutFromChannel(outFromChannel);
                schSession.setHostSystem(hostSystem);



            } catch (Exception e) {
                log.info(e.toString(), e);
                hostSystem.setErrorMsg(e.getMessage());
                if (e.getMessage().toLowerCase().contains("userauth fail")) {
                    hostSystem.setStatusCd(HostSystem.PUBLIC_KEY_FAIL_STATUS);
                } else if (e.getMessage().toLowerCase().contains("auth fail") || e.getMessage().toLowerCase().contains("auth cancel")) {
                    hostSystem.setStatusCd(HostSystem.AUTH_FAIL_STATUS);
                } else if (e.getMessage().toLowerCase().contains("unknownhostexception")) {
                    hostSystem.setErrorMsg("DNS Lookup Failed");
                    hostSystem.setStatusCd(HostSystem.HOST_FAIL_STATUS);
                } else {
                    hostSystem.setStatusCd(HostSystem.GENERIC_FAIL_STATUS);
                }
            }

            //add session to map
            if (hostSystem.getStatusCd().equals(HostSystem.SUCCESS_STATUS)) {
                //get the server maps for user
                UserSchSessions userSchSessions = userSchSessionMap.get(sessionId);

                //if no user session create a new one
                if (userSchSessions == null) {
                    userSchSessions = new UserSchSessions();
                }
                Map<Integer, SchSession> schSessionMap = userSchSessions.getSchSessionMap();

                //add server information
                schSessionMap.put(instanceId, schSession);
                userSchSessions.setSchSessionMap(schSessionMap);
                //add back to map
                userSchSessionMap.put(sessionId, userSchSessions);
            }
        }

        return hostSystems;
    }

    /**
     * 获取会话中下个实例id
     *
     * @param sessionId      session id
     * @param userSessionMap user session map
     * @return
     */
    private static int getNextInstanceId(String sessionId, Map<String, UserSchSessions> userSessionMap) {

        Integer instanceId = 1;
        if (userSessionMap.get(sessionId) != null) {

            for (Integer id : userSessionMap.get(sessionId).getSchSessionMap().keySet()) {
//				if(id >instanceId){
//					instanceId = id;
//				}
                if (!id.equals(instanceId) && userSessionMap.get(sessionId).getSchSessionMap().get(instanceId) == null) {
                    return instanceId;
                }
                instanceId = instanceId + 1;
            }
        }
        return instanceId;

    }


    /**
     * 上传文件
     *
     * @param schSession     an established SSH session
     * @param source      source file
     * @param destination destination file
     * @return status uploaded file
     */

    protected static HostSystem pushUpload(SchSession schSession, String source, String destination) {

        HostSystem hostSystem  = schSession.getHostSystem();
        Session session  = schSession.getSession();
        hostSystem.setStatusCd(HostSystem.SUCCESS_STATUS);
        Channel channel = null;
        ChannelSftp c = null;
        FileInputStream file = null;
        try  {
            file = new FileInputStream(source);
            channel = session.openChannel("sftp");
            channel.setInputStream(System.in);
            channel.setOutputStream(System.out);
            channel.connect(CHANNEL_TIMEOUT);

            c = (ChannelSftp) channel;
            destination = destination.replaceAll("~\\/|~", "");
            c.put(file, destination);
        } catch (Exception e) {
            log.info(e.toString(), e);
            hostSystem.setErrorMsg(e.getMessage());
            hostSystem.setStatusCd(HostSystem.GENERIC_FAIL_STATUS);
        }finally{
            if(file != null){
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //exit
        if (c != null) {
            c.exit();
        }
        //disconnect
        if (channel != null) {
            channel.disconnect();
        }

        return hostSystem;


    }

    /**
     * 下载文件
     * @param schSession
     * @param path
     * @return
     */
    protected static ResponseEntity<byte[]> download(SchSession schSession, String path){

        JSch jsch = new JSch();
        Session  session = schSession.getSession();
        Channel channel = null;
        ChannelSftp sftp = null;
        InputStream  input= null;
        byte[] body=null;
        ResponseEntity<byte[]> response = null;
        try {
            channel = session.openChannel("sftp");
            channel.setInputStream(System.in);
            channel.setOutputStream(System.out);
            channel.connect(CHANNEL_TIMEOUT);
            sftp = (ChannelSftp)channel;
            input = sftp.get(path);

            body = new byte[0];
            byte[] tmp = new byte[102400];
            int count = -1;
            while((count = input.read(tmp)) != -1){
                byte[] _body = new byte[body.length+count];
                System.arraycopy(body, 0, _body, 0, body.length);
                System.arraycopy(tmp, 0, _body, body.length, count);
                body = _body;
//				System.out.println(new String(tmp,0,count));
            }

            String fileName = path.substring(path.lastIndexOf("/")+1);
            HttpHeaders headers=new HttpHeaders();
            //响应头的名字和响应头的值
            headers.add("Content-Disposition", "attachment;filename="+fileName);
            HttpStatus statusCode=HttpStatus.OK;

            response=new ResponseEntity<byte[]>(body, headers, statusCode);
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{

            if(input != null){
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }

        //exit
        if (sftp != null) {
            sftp.exit();
        }
        //disconnect
        if (channel != null) {
            channel.disconnect();
        }

        return response;

    }

    /**
     *列出指定目录的文件集合
     * @param schSession
     * @param path
     * @return
     */
    protected static Vector<ChannelSftp.LsEntry> listDirectory(SchSession schSession, String path) {

        Vector<ChannelSftp.LsEntry> ret = null;
        HostSystem hostSystem  = schSession.getHostSystem();
        Session session  = schSession.getSession();
        hostSystem.setStatusCd(HostSystem.SUCCESS_STATUS);
        Channel channel = null;
        ChannelSftp sftp = null;
        FileInputStream file = null;
        try  {
            channel = session.openChannel("sftp");
            channel.setInputStream(System.in);
            channel.setOutputStream(System.out);
            channel.connect(CHANNEL_TIMEOUT);
            sftp = (ChannelSftp) channel;
            ret = sftp.ls(path);

//			for(ChannelSftp.LsEntry lsEntry : ret){
//				SftpATTRS attrs = lsEntry.getAttrs();
//				System.out.println(lsEntry.getFilename()+",isBlk: "+attrs.isBlk()+",isChr"+attrs.isChr()+",isDir"+attrs.isDir()+",isFifo"+attrs.isFifo()+",isLink"+attrs.isLink()+",isReg"+attrs.isReg()+",isSock"+attrs.isSock());
//
//			}
        } catch (Exception e) {
            hostSystem.setErrorMsg(e.getMessage());
            hostSystem.setStatusCd(HostSystem.GENERIC_FAIL_STATUS);
        }finally{
            if(file != null){
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //exit
        if (sftp != null) {
            sftp.exit();
        }
        //disconnect
        if (channel != null) {
            channel.disconnect();
        }
        if(ret != null && ret.size()>1){
            Collections.sort(ret);
        }
//		Collections.sort(ret, new  Comparator<ChannelSftp.LsEntry>(){
//			@Override
//			public int compare(LsEntry o1, LsEntry o2) {
//				return o1.getFilename().compareTo(o2.getFilename());
//			}
//		});
        return ret;

    }

    /**
     * 下载文件（该方法返回下载的文件，适用于后台下载不需返回页面或由controller自己决定返回至页面）
     * @param hostSystem
     * @param path
     * @return
     */
    public static File downloadFile(HostSystem hostSystem,String path){
        File downFile = null;
        JSch jsch = new JSch();
        Session  session = null;
        Channel channel = null;
        ChannelSftp sftp = null;
        InputStream  input= null;
        FileOutputStream out = null ;
        try {
            session = jsch.getSession(hostSystem.getUser(), hostSystem.getHost(), hostSystem.getPort());
            session.setPassword(hostSystem.getPassword());
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            session.setServerAliveInterval(SERVER_ALIVE_INTERVAL);
            session.connect(SESSION_TIMEOUT);

            channel = session.openChannel("sftp");
            channel.setInputStream(System.in);
            channel.setOutputStream(System.out);
            channel.connect(CHANNEL_TIMEOUT);
            sftp = (ChannelSftp)channel;
            input = sftp.get(path);
            String rootPath = null;
            try {
                rootPath = SSHCommander.class.getResource("/").toURI().getPath()+"/tmp";
            } catch (URISyntaxException e) {
                rootPath = SSHCommander.class.getResource("/").getPath()+"/tmp";
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String filePath = df.format(new Date()) + "_" + new java.util.Random().nextInt(1000);
            String fileName = path.substring(path.lastIndexOf("/")+1);

            File fileDirectory = new File(rootPath+"/"+filePath);
            if(!fileDirectory.exists()){
                fileDirectory.mkdirs();
            }
            downFile = new File(fileDirectory.getAbsoluteFile()+"/"+fileName);
            out = new FileOutputStream(downFile);

            byte[] tmp = new byte[1024];
            int count = -1;
            while((count = input.read(tmp)) != -1){
                out.write(tmp,0,count);
            }
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(input != null){
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }

        //exit
        if (sftp != null) {
            sftp.exit();
        }
        //disconnect
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
        return downFile;
    }

    /**
     * 下载文件（controller把该方法值直接返回即可）
     * @param hostSystem
     * @param path
     * @return
     */
    public static ResponseEntity<byte[]>  download(HostSystem hostSystem,String path){

        JSch jsch = new JSch();
        Session  session = null;
        Channel channel = null;
        ChannelSftp sftp = null;
        InputStream  input= null;
        byte[] body=null;
        ResponseEntity<byte[]> response = null;
        try {
            session = jsch.getSession(hostSystem.getUser(), hostSystem.getHost(), hostSystem.getPort());
            session.setPassword(hostSystem.getPassword());
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            session.setServerAliveInterval(SERVER_ALIVE_INTERVAL);
            session.connect(SESSION_TIMEOUT);

            channel = session.openChannel("sftp");
            channel.setInputStream(System.in);
            channel.setOutputStream(System.out);
            channel.connect(CHANNEL_TIMEOUT);
            sftp = (ChannelSftp)channel;
            input = sftp.get(path);

            body = new byte[0];
            byte[] tmp = new byte[1024];
            int count = -1;
            while((count = input.read(tmp)) != -1){
                byte[] _body = new byte[body.length+count];
                System.arraycopy(body, 0, _body, 0, body.length);
                System.arraycopy(tmp, 0, _body, body.length, count);
                body = _body;
//				System.out.println(new String(tmp,0,count));
            }

            String fileName = path.substring(path.lastIndexOf("/")+1);
            HttpHeaders headers=new HttpHeaders();
            //响应头的名字和响应头的值
            headers.add("Content-Disposition", "attachment;filename="+fileName);
            HttpStatus statusCode=HttpStatus.OK;

            response=new ResponseEntity<byte[]>(body, headers, statusCode);
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(input != null){
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }

        //exit
        if (sftp != null) {
            sftp.exit();
        }
        //disconnect
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
        return response;

    }


    /**
     * 上传文件
     * @param hostSystem
     * @param files
     * @param destination
     * @return
     */
    public static boolean upload(HostSystem hostSystem, List<File> files, String destination) {
        return upload(hostSystem,files,destination,ChannelSftp.OVERWRITE);
    }


    /**
     * 上传文件
     * @param hostSystem
     * @param files
     * @param destination
     * @param mode  0: OVERWRITE, 1: RESUME, 2: APPEND
     * @return
     */
    public static boolean upload(HostSystem hostSystem, List<File> files, String destination, int mode) {

        hostSystem.setStatusCd(HostSystem.SUCCESS_STATUS);
        JSch jsch = new JSch();
        Session  session = null;
        Channel channel = null;
        ChannelSftp sftp = null;
        byte[] body=null;
        try  {
            session = jsch.getSession(hostSystem.getUser(), hostSystem.getHost(), hostSystem.getPort());
            session.setPassword(hostSystem.getPassword());
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            session.setServerAliveInterval(SERVER_ALIVE_INTERVAL);
            session.connect(SESSION_TIMEOUT);

            channel = session.openChannel("sftp");
            channel.setInputStream(System.in);
            channel.setOutputStream(System.out);
            channel.connect(CHANNEL_TIMEOUT);
            sftp = (ChannelSftp)channel;
            for(File file :files){
                sftp.put(file.getAbsolutePath(), destination,mode);
            }

        } catch (Exception e) {
            log.info(e.toString(), e);
            hostSystem.setErrorMsg(e.getMessage());
            hostSystem.setStatusCd(HostSystem.GENERIC_FAIL_STATUS);
        }finally{

        }
        //exit
        if (sftp != null) {
            sftp.exit();
        }
        //disconnect
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }

        return true;


    }




    public static void main(String[] args) {
//		HostSystem hostSystem = new HostSystem();
//		 hostSystem.setHost("192.168.161.26");
//		 hostSystem.setUser("bp_dcf");
//		 hostSystem.setPassword("dic123");
//		 List<File>  files = new ArrayList<File>();
//		 files.add(new File("D:/开源项目/codemirror.zip"));
//		 SSHCommander.upload(hostSystem, files, "/public/bp");
//		 File file = SSHCommander.downloadFile(hostSystem, "/public/bp/tp.sh");
//		System.out.println(file.getAbsolutePath());


        JSch jsch = new JSch();
        Session  session = null;
        Channel channel = null;
        ChannelSftp sftp = null;
        BufferedInputStream buffer = null;
        try  {
            session = jsch.getSession("ah_test", "192.168.161.27", 22);
            session.setPassword("Ah_test@789#");
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            session.setServerAliveInterval(SERVER_ALIVE_INTERVAL);
            session.connect(SESSION_TIMEOUT);

            channel = session.openChannel("sftp");
            channel.setInputStream(System.in);
            channel.setOutputStream(System.out);
            channel.connect(CHANNEL_TIMEOUT);
            sftp = (ChannelSftp)channel;
            Vector<ChannelSftp.LsEntry> list = sftp.ls("/public/ocs/ah_test/myapp/env/nginx");
            for(ChannelSftp.LsEntry lsEntry : list){
                SftpATTRS  attrs = lsEntry.getAttrs();
                String str = attrs.getPermissionsString()+" "+attrs.getUId()+" "+attrs.getGId()+" "+attrs.getSize()+" "+attrs.getMtimeString()+" "+lsEntry.getFilename();
                System.out.println(str);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }finally{

        }
        //exit
        if (sftp != null) {
            sftp.exit();
        }
        //disconnect
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }

    }




}
