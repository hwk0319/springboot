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
import com.it.po.Database;
import com.it.po.UserInfo;
import com.it.service.operations.DatabaseService;
import com.it.util.JDBCConnect;
import com.it.util.Result;

/**
 * 
 * @ClassName: DatabaseController  
 * @Description: TODO  数据库管理
 * @author Administrator  
 * @date 2020年10月10日  
 *
 */
@Controller
@RequestMapping(value="/database")
public class DatabaseController {
	private static final Logger log = LoggerFactory.getLogger(DatabaseController.class);

	@Resource
	private DatabaseService service;
	
	@RequestMapping(value="/list")
	public String list() {
		return "operations/database/databaseList";
	}
	
	/**
	 * 
	 * @Title: select  
	 * @Description: TODO  分页查询数据库
	 * @param @param po
	 * @param @return    参数  
	 * @return Result    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/select")
	@ResponseBody
	public Result select(Database po) {
		PageHelper.startPage(po.getPageNo(), po.getLimit());
		List<Database> list = service.select(po);
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
	@OperLogs(value = "新增数据库")
	@RequiresPermissions(value="database:insert")
	@ResponseBody
	public Result insert(Database po) {
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
	 * @Description: TODO  编辑数据库
	 * @param @param po
	 * @param @return    参数  
	 * @return Result    返回类型  
	 * @throws
	 */
	@RequestMapping("/update")
	@RequiresPermissions(value="database:update")
	@OperLogs(value = "编辑数据库")
	@ResponseBody
	public Result update(Database po) {
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
		Database database = service.selectById(id);
		return Result.success(database);
	}
	
	/**
	 * 
	 * @Title: delete  
	 * @Description: TODO  删除数据库
	 * @param @param id
	 * @param @return    参数  
	 * @return Result    返回类型  
	 * @throws
	 */
	@RequestMapping("/delete")
	@OperLogs(value = "删除数据库")
	@RequiresPermissions(value="database:delete")
	@ResponseBody
	public Result delete(@RequestParam(name = "id") Integer id) {
		int res = service.deleteById(id);
		return Result.success(res);
	}
	
	/**
	 * 
	 * @Title: batchDelete  
	 * @Description: TODO  批量删除数据库
	 * @param @param ids
	 * @param @return    参数  
	 * @return Result    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/batchDelete")
	@OperLogs(value = "批量删除数据库")
	@RequiresPermissions(value="database:delete")
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
	 * @Description: TODO  测试数据库连接
	 * @param @param ip
	 * @param @param port
	 * @param @param account
	 * @param @param password
	 * @param @return    参数  
	 * @return boolean    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/testConnect")
	@ResponseBody
	public boolean testConnect(String url, String account, String password) {
		boolean result = JDBCConnect.testConnect(url, account, password);
		return result;
	}
}
