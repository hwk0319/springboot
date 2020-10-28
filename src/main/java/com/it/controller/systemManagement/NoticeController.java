package com.it.controller.systemManagement;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.it.aspect.OperLogs;
import com.it.po.Notice;
import com.it.po.UserInfo;
import com.it.service.systemManagement.NoticeService;
import com.it.util.Result;
import com.it.util.Util;

/**
 * 
 * @ClassName: NoticeController  
 * @Description: TODO  通知公告
 * @author Administrator  
 * @date 2020年9月23日  
 *
 */
@Controller
@RequestMapping("/notice")
public class NoticeController {

	private Logger log = LoggerFactory.getLogger(NoticeController.class);
	
	@Resource
	private NoticeService service;
	
	@RequestMapping("/list")
	public String noticeList() {
		return "system/notice/noticeList";
	}
	
	
	/**
	 * 
	 * @Title: search  
	 * @Description: TODO  分页查询通知公告
	 * @param @param po
	 * @param @return    参数  
	 * @return Result    返回类型  
	 * @throws
	 */
	@RequestMapping("/search")
	@ResponseBody
	public Result search(Notice po) {
		PageHelper.startPage(po.getPageNo(), po.getLimit());
		List<Notice> list = service.search(po);
		return Result.success(list, list.size());
	}
	
	/**
	 * 
	 * @Title: insert  
	 * @Description: TODO  新增通知公告
	 * @param @param po
	 * @param @return    参数  
	 * @return Result    返回类型  
	 * @throws
	 */
	@RequestMapping("/insert")
	@OperLogs(value = "新增通知公告")
	@RequiresPermissions(value="notice:insert")
	@ResponseBody
	public Result insert(Notice po) {
		UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
		po.setCreator(user.getName());
		po.setStatus(1);
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
	 * @Description: TODO  更新通知公告
	 * @param @param po
	 * @param @return    参数  
	 * @return Result    返回类型  
	 * @throws
	 */
	@RequestMapping("/update")
	@RequiresPermissions(value="notice:update")
	@OperLogs(value = "更新通知公告")
	@ResponseBody
	public Result update(Notice po) {
		int res = 0;
		try {
			res = service.update(po);
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
		Notice notice = service.selectById(id);
		return Result.success(notice);
	}
	
	/**
	 * 
	 * @Title: delete  
	 * @Description: TODO  根据id删除
	 * @param @param id
	 * @param @return    参数  
	 * @return Result    返回类型  
	 * @throws
	 */
	@RequestMapping("/delete")
	@OperLogs(value = "删除通知公告")
	@RequiresPermissions(value="notice:delete")
	@ResponseBody
	public Result delete(@RequestParam(name = "id") Integer id) {
		int res = service.deleteById(id);
		return Result.success(res);
	}
	
	/**
	 * 
	 * @Title: bacthDeleteUser  
	 * @Description: TODO  批量删除通知公告
	 * @param @param ids
	 * @param @return    参数  
	 * @return Result    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/batchDelete")
	@OperLogs(value = "批量删除通知公告")
	@RequiresPermissions(value="notice:delete")
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
}
