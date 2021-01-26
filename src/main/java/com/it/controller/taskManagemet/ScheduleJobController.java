package com.it.controller.taskManagemet;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.it.aspect.OperLogs;
import com.it.controller.BaseController;
import com.it.po.ScheduleJob;
import com.it.po.ScheduleJobLog;
import com.it.quartz.Constant;
import com.it.service.taskManagement.ScheduleJobLogService;
import com.it.service.taskManagement.ScheduleJobService;
import com.it.util.Result;

/**
 * 
 * @ClassName: ScheduleJobController  
 * @Description: TODO  定时任务
 * @author Administrator  
 * @date 2020年9月9日  
 *
 */
@Controller
@RequestMapping(value="/schedule")
public class ScheduleJobController extends BaseController{
	
	@Resource(name="scheduleJobService")
	private ScheduleJobService service;
	
	@Resource(name="scheduleJobLogService")
	private ScheduleJobLogService taskLogService;
	/**
	 * 跳转页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/taskList")
	@RequiresPermissions("schedule:taskList")
	public ModelAndView list(Model model) {
		return new ModelAndView("task/schedule/scheduleList");
	}
	
	/**
	 * 
	 * @Title: searchById  
	 * @Description: TODO  根据id查询
	 * @param @param po
	 * @param @return    参数  
	 * @return ScheduleJob   返回类型  
	 * @throws
	 */
	@RequestMapping(value="/selectById")
	@ResponseBody
	public ScheduleJob selectById(ScheduleJob po){
		ScheduleJob scheduleJob = service.selectById(po.getId());
		return scheduleJob;
	}
	
	/**
	 * 查询数据并返回json数据
	 * @param po
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/selectList")
	@RequiresPermissions("schedule:selectList")
	@ResponseBody
	public Result selectList(ScheduleJob po, HttpServletRequest request){
		PageHelper.startPage(po.getPageNo(), po.getLimit());
		List<ScheduleJob> list = service.selectList(po);
		return Result.success(list, list.size());
	}
	
	/**
	 * 
	 * @Title: insert  
	 * @Description: TODO  新增数据
	 * @param @param po
	 * @param @param request
	 * @param @return
	 * @param @throws Exception    参数  
	 * @return int    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/insert")
	@RequiresPermissions("schedule:insert")
	@OperLogs(value = "新增定时任务")
	@ResponseBody
	public int insert(ScheduleJob po,HttpServletRequest request) throws Exception{		
		int res = service.insert(po);
		return res;
	}
	
	/**
	 * 
	 * @Title: update  
	 * @Description: TODO  编辑
	 * @param @param po
	 * @param @param request
	 * @param @return
	 * @param @throws Exception    参数  
	 * @return int    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/update")
	@RequiresPermissions(value = "schedule:update")
	@OperLogs(value = "编辑定时任务")
	@ResponseBody
	public int update(ScheduleJob po,HttpServletRequest request) throws Exception{
		int res = service.update(po);
		return res;
	}
	
	/**
	 * 
	 * @Title: delete  
	 * @Description: TODO  删除
	 * @param @param po
	 * @param @param request
	 * @param @return    参数  
	 * @return int    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/delete")
	@RequiresPermissions(value = "schedule:delete")
	@OperLogs(value = "删除定时任务")
	@ResponseBody
	public int delete(ScheduleJob po, HttpServletRequest request) throws Exception{		
		int res = service.delete(po.getId());
		return res;
	}
	
	/**
	 * 
	 * @Title: pause  
	 * @Description: TODO  暂停任务
	 * @param @param po
	 * @param @param request
	 * @param @return
	 * @param @throws Exception    参数  
	 * @return int    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/pause")
	@RequiresPermissions(value = "schedule:pause")
	@OperLogs(value = "暂停定时任务")
	@ResponseBody
	public Result pause(ScheduleJob po, HttpServletRequest request) throws Exception{		
		int res = 0;
		String[] ids = po.getIds().split(",");
		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];
			po.setId(Integer.valueOf(id));
			po.setStatus(Constant.PAUSE);
			res = service.pause(po);
		}
		return Result.success(res);
	}
	
	/**
	 * 
	 * @Title: execute  
	 * @Description: TODO  执行任务
	 * @param @param po
	 * @param @param request
	 * @param @return
	 * @param @throws Exception    参数  
	 * @return int    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/execute")
	@RequiresPermissions(value = "schedule:execute")
	@OperLogs(value = "执行定时任务")
	@ResponseBody
	public Result execute(ScheduleJob po, HttpServletRequest request) throws Exception{		
		int res = 0;
		String[] ids = po.getIds().split(",");
		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];
			po.setId(Integer.valueOf(id));
			po.setStatus(Constant.NORMAL);
			res = service.execute(po);
		}
		return Result.success(res);
	}
	
	/**
	 * 
	 * @Title: selectList  
	 * @Description: TODO  查询任务日志
	 * @param @param po
	 * @param @return    参数  
	 * @return List<ScheduleJobLog>    返回类型  
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/selectTaskLog")
	public Result selectList(ScheduleJobLog po){
		PageHelper.startPage(po.getPageNo(), po.getLimit());
		List<ScheduleJobLog> list = taskLogService.selectList(po);
		return Result.success(list, list.size());
	}
	
	/**
	 * 
	 * @Title: deleteAll  
	 * @Description: TODO  清空任务日志
	 * @param @return    参数  
	 * @return Result    返回类型  
	 * @throws
	 */
	@ResponseBody
	@OperLogs(value="清空任务日志")
	@RequestMapping(value="/deleteAll")
	public Result deleteAll(@RequestParam(name="jobId") Integer jobId) {
		int res = taskLogService.deleteAll(jobId);
		return Result.success(res);
	}
	
	/**
	 * 
	 * @Title: bacthDelete  
	 * @Description: TODO  批量删除任务
	 * @param @param ids
	 * @param @return    参数  
	 * @return Result    返回类型  
	 * @throws
	 */
	@ResponseBody
	@OperLogs(value="批量删除任务")
	@RequestMapping(value="/bacthDelete")
	public Result bacthDelete(@RequestParam(name = "ids") String ids) {
		String[] array = ids.split(",");
		int res = 0;
		try {
			res = service.bacthDelete(array);
			if(res > 0) {
				for (int i = 0; i < array.length; i++) {
					int jobId = Integer.valueOf(array[i]);
					taskLogService.deleteAll(jobId);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return Result.success(res);
	}
}
