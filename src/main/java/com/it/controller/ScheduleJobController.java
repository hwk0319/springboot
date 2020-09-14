package com.it.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.aspect.OperLogs;
import com.it.po.ScheduleJob;
import com.it.po.ScheduleJobLog;
import com.it.po.UserInfo;
import com.it.quartz.Constant;
import com.it.service.ScheduleJobLogService;
import com.it.service.ScheduleJobService;
import com.it.util.JsonDateValueProcessor;
import com.it.util.JsonDefaultValueProcessor;
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
@RequestMapping(value="/task")
public class ScheduleJobController {
	@Resource(name="scheduleJobService")
	private ScheduleJobService service;
	
	@Resource(name="scheduleJobLogService")
	private ScheduleJobLogService taskLogService;
	
	private Logger logger = LoggerFactory.getLogger(ScheduleJobController.class);
	
	/**
	 * 跳转页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/taskList")
	public ModelAndView list(Model model) {
		return new ModelAndView("task/taskList");
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
	@ResponseBody
	public JSONObject selectList(ScheduleJob po, HttpServletRequest request){
		
		PageHelper.startPage(po.getPageNo(), po.getLimit());
		List<ScheduleJob> list = service.selectList(po);
		PageInfo<ScheduleJob> list1 = new PageInfo<ScheduleJob>(list);

		//转成JSONArray
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());//时间格式转换
		config.registerDefaultValueProcessor(Integer.class, new JsonDefaultValueProcessor());//数据格式转换
		JSONArray json = JSONArray.fromObject(list,config); 
		
		JSONObject jsonObject = new JSONObject();  //创建Json对象
		jsonObject.put("total", list1.getTotal());//总记录数
		jsonObject.put("rows", json);//json数据
		
		return jsonObject;
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
	@RequiresPermissions("task:insert")
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
	@RequiresPermissions(value = "task:update")
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
	@RequiresPermissions(value = "task:delete")
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
	@RequiresPermissions(value = "task:pause")
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
	@RequiresPermissions(value = "task:execute")
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
		PageInfo<ScheduleJobLog> list1 = new PageInfo<ScheduleJobLog>(list);
		return Result.successTotal(list, String.valueOf(list1.getTotal()));
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
	public Result deleteAll() {
		int res = taskLogService.deleteAll();
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
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return Result.success(res);
	}
}
