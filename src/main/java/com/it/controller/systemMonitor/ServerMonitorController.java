package com.it.controller.systemMonitor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.it.po.monitor.ServerMonitor;

/**
 * 
 * @ClassName: ServerMonitorController  
 * @Description: TODO  服务监控
 * @author Administrator  
 * @date 2020年10月28日  
 *
 */
@Controller
@RequestMapping("/monitor")
public class ServerMonitorController {

	@RequestMapping("/server")
	public String serverMonitor(ModelMap model) throws Exception {
		ServerMonitor server = new ServerMonitor();
	    server.copyTo();
	    model.put("server", server);
		return "monitor/serverMonitor";
	}
}
