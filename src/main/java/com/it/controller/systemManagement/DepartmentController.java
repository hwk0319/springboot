package com.it.controller.systemManagement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.it.aspect.OperLogs;
import com.it.po.Department;
import com.it.service.systemManagement.DepartmentService;
import com.it.util.JsonDateValueProcessor;
import com.it.util.JsonDefaultValueProcessor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


@RestController
@RequestMapping(value="department")
public class DepartmentController {
	@Resource(name="departmentService")
	private DepartmentService service;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 跳转到department页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Model model) {
		return new ModelAndView("system/department/departmentList");
	}
	/**
	 * 跳转到departmentAdd页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/departmentAdd")
	public ModelAndView Add(Model model) {
		return new ModelAndView("system/department/departmentAdd");
	}

	/**
	 * 查询用户
	 * @param po
	 * @return
	 */
	@RequestMapping(value="/search")
	public List<Department> search(Department po){
		List<Department> department = new ArrayList<Department>();
		try {
			department = service.search(po);
		} catch (Exception e) {
		}
		return department;
	}
	
	/**
	 * 
	 * @Title: findById  
	 * @Description: TODO  根据id查询
	 * @param @param id
	 * @param @return    参数  
	 * @return Department    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/findById")
	public Department findById(int id) {
		Department department = null;
		try {
			department = service.findById(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return department;
	}
	
	/**
	 * 
	 * @Title: findParentById  
	 * @Description: TODO  根据id查询上级部门
	 * @param @param pid
	 * @param @return    参数  
	 * @return Department    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/findParentById")
	public Department findParentById(int id) {
		Department department = null;
		try {
			department = service.findParentById(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return department;
	}
	
	
	
	/**
	 * 查询用户列表
	 * @param po
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/search2")
	public JSONObject search1(Department po, HttpServletRequest request){
		
		List<Department> list = service.search(po);

		//转成JSONArray
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());//时间格式转换
		config.registerDefaultValueProcessor(Integer.class, new JsonDefaultValueProcessor());//数据格式转换
		JSONArray json = JSONArray.fromObject(list,config); 
		
		JSONObject jsonObject = new JSONObject();  //创建Json对象
		jsonObject.put("total", list.size());//总记录数
		jsonObject.put("rows", json);//json数据
		
		return jsonObject;
	}
	
	/*@RequestMapping(value="/search3")
	public Result search3(Department po, HttpServletRequest request) {
		PageHelper.startPage(po.getPageNo(), po.getLimit());
		List<Department> list = service.search(po);
		PageInfo<Department> list1 = new PageInfo<Department>(list);
		return Result.markSuccessTotal(list, String.valueOf(list1.getTotal()));
	}*/
	
	/**
	 * 保存
	 * @author ydd
	 * @param name
	 * @param departmentId
	 * @param age
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/insert")
	@RequiresPermissions("department:add")
	@OperLogs(value = "新增部门")
	public int insert(Department po,HttpServletRequest request) throws Exception{	
		int res = 0;
		try {
			res = service.insert(po);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return res;
	}
	/**
	 * 编辑用户
	 * @param po
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/update")
	@RequiresPermissions(value = "department:edit")
	@OperLogs(value = "编辑部门")
	public int update(Department po,HttpServletRequest request) throws Exception{
		int res = 0;
		try {
			res = service.update(po);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 注销用户
	 * @param po
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@RequiresPermissions(value = "department:delete")
	@OperLogs(value = "删除部门")
	public int delete(Department po,HttpServletRequest request) throws Exception{		
		int res =  service.delete(po);
		return res;
	}

	/**
	 * 
	 * @Title: getTree  获取部门树
	 * @Description: TODO  
	 * @param @param po
	 * @param @param request
	 * @param @return    参数  
	 * @return JSONArray    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/getTree")
	public JSONArray getTree(Department po,HttpServletRequest request){
		List<Department> list = service.search(po);
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
			
		for (int i = 0; i < list.size(); i++) {
			int id = list.get(i).getId();
			int pId = list.get(i).getPid();
			String name = list.get(i).getName();
			boolean open = false;
			for (int j = 0; j < list.size(); j++) {
				if(String.valueOf(id).equals(String.valueOf(list.get(j).getPid()))) {
					open = true;
					break;
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("pId", pId);
			map.put("name", name);
			map.put("open", open);
			list2.add(map);
		}
		//转成JSONArray
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());//时间格式转换
		config.registerDefaultValueProcessor(Integer.class, new JsonDefaultValueProcessor());//数据格式转换
		JSONArray json = JSONArray.fromObject(list2,config); 
		return json;
	}

}
