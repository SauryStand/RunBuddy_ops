package com.thinkgem.jeesite.modules.host.web;

import com.runbuddy.modules.ssh.model.HostSystem;
import com.runbuddy.modules.ssh.web.SSHCommander;
import com.runbuddy.utils.config.FrameConfigKey;
import com.runbuddy.utils.tools.BlankUtil;
import com.runbuddy.utils.tools.DesTool;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.host.service.CoreService;
import com.thinkgem.jeesite.modules.host.service.HostService;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/5.
 */

@Controller
@RequestMapping("${adminPath}/host")
public class HostController extends BaseController {

    private static Logger logger = Logger.getLogger(HostController.class);

    private HostService hostService;
    /**
     * 核心Service
     */
    //@Autowired
    private CoreService coreService;

    @RequiresPermissions("sys:user:host")
    @RequestMapping(value = "/list")
    public String hostInfo(HttpServletRequest request,HttpServletResponse response){

        logger.debug("测试终端输出");

        return "modules/host/hostManage";
    }


    /**
     * 终端操作
     * 如果通过代理打开终端，需要在代理中添加下面配置:
     * proxy_set_header        Upgrade $http_upgrade;
     * proxy_set_header        Connection "upgrade";
     * proxy_read_timeout      设置超时时间
     * @param request
     * @return
     */
    @RequestMapping(method= RequestMethod.POST, value = "/terminal")
    public String insertCompute(HttpServletRequest request, @RequestParam("termialHost") String host) {
        logger.debug("跳转到终端测试, 当前主机列表 ---> " + host);
        List<HostSystem> hosts = new ArrayList<HostSystem>();
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("HOST_IDS", host);
        List<HashMap<String, String>> hostList = coreService.queryForList("host.queryHostForTermial", queryParams, FrameConfigKey.DEFAULT_DATASOURCE);
        if (!BlankUtil.isBlank(hostList)) {
            for (int i=0; i<hostList.size(); i++) {
                HostSystem hostSystem = new HostSystem();
                String hostIp = hostList.get(i).get("HOST_IP");
                String hostUserName = hostList.get(i).get("SSH_USER");
                String hostPassword = hostList.get(i).get("SSH_PASSWD");
                hostSystem.setHost(hostIp);
                hostSystem.setUser(hostUserName);
                hostSystem.setPassword(DesTool.dec(hostPassword));
                hosts.add(hostSystem);
            }
        }
        String forward = SSHCommander.openSSHTermOnSystem(request, hosts);
        logger.debug("终端跳转页面 ---> " + forward);
        return forward;
    }



}
