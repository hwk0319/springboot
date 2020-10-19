package com.it.controller.operations;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.it.aspect.OperLogs;
import com.it.po.Server;
import com.it.po.UserInfo;
import com.it.service.operations.ServerService;
import com.it.util.Result;
import com.it.util.SSHConnect;

/**
 * 
 * @ClassName: ServerController  
 * @Description: TODO  服务器管理
 * @author Administrator  
 * @date 2020年9月28日  
 *
 */
@Controller
@RequestMapping(value="server")
public class ServerController {
	private static final Logger log = LoggerFactory.getLogger(ServerController.class);
	
	@Resource
	private ServerService service;

	@RequestMapping(value="/list")
	public String list() {
		return "operations/server/serverList";
	}
	
	/**
	 * 
	 * @Title: select  
	 * @Description: TODO  分页查询服务器
	 * @param @param po
	 * @param @return    参数  
	 * @return Result    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/select")
	@ResponseBody
	public Result select(Server po) {
		PageHelper.startPage(po.getPageNo(), po.getLimit());
		List<Server> list = service.select(po);
		return Result.successTotal(list, String.valueOf(list.size()));
	}
	
	/**
	 * 
	 * @Title: insert  
	 * @Description: TODO  
	 * @param @param po
	 * @param @return    参数  
	 * @return Result    返回类型  
	 * @throws
	 */
	@RequestMapping("/insert")
	@OperLogs(value = "新增服务器")
	@RequiresPermissions(value="server:insert")
	@ResponseBody
	public Result insert(Server po) {
		UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
		po.setCreator(user.getName());
		int res = 0;
		try {
			res = service.insert(po);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.getStackTrace();
		}
		return Result.success(res);
	}
	
	/**
	 * 
	 * @Title: update  
	 * @Description: TODO  编辑服务器
	 * @param @param po
	 * @param @return    参数  
	 * @return Result    返回类型  
	 * @throws
	 */
	@RequestMapping("/update")
	@RequiresPermissions(value="server:update")
	@OperLogs(value = "编辑服务器")
	@ResponseBody
	public Result update(Server po) {
		UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
		po.setUpdator(user.getName());
		int res = 0;
		try {
			res = service.updateByPrimaryKeySelective(po);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.getStackTrace();
		}
		return Result.success(res);
	}
	
	/**
	 * 
	 * @Title: selectById  
	 * @Description: TODO  根据id查询
	 * @param @param id
	 * @param @return    参数  
	 * @return Result    返回类型  
	 * @throws
	 */
	@RequestMapping("/selectById")
	@ResponseBody
	public Result selectById(@RequestParam(name = "id") Integer id) {
		Server server = service.selectById(id);
		return Result.success(server);
	}
	
	/**
	 * 
	 * @Title: delete  
	 * @Description: TODO  删除服务器
	 * @param @param id
	 * @param @return    参数  
	 * @return Result    返回类型  
	 * @throws
	 */
	@RequestMapping("/delete")
	@OperLogs(value = "删除服务器")
	@RequiresPermissions(value="server:delete")
	@ResponseBody
	public Result delete(@RequestParam(name = "id") Integer id) {
		int res = service.deleteById(id);
		return Result.success(res);
	}
	
	/**
	 * 
	 * @Title: batchDelete  
	 * @Description: TODO  批量删除服务器
	 * @param @param ids
	 * @param @return    参数  
	 * @return Result    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/batchDelete")
	@OperLogs(value = "批量删除服务器")
	@RequiresPermissions(value="server:delete")
	@ResponseBody
	public Result batchDelete(@RequestParam(name = "ids") String ids){	
		String[] array = ids.split(",");
		int res = 0;
		try {
			res = service.batchDelete(array);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return Result.success(res);
	}
	
	/**
	 * 
	 * @Title: testConnect  
	 * @Description: TODO  测试SSH连接
	 * @param @param ip
	 * @param @param port
	 * @param @param account
	 * @param @param password
	 * @param @return    参数  
	 * @return boolean    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/testSSHConnect")
	@ResponseBody
	public boolean testSSHConnect(String ip, int port, String account, String password) {
		boolean result = SSHConnect.testSSHConnect(ip, port, account, password);
		return result;
	}
}
