package com.it.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.aspect.OperLogs;
import com.it.po.Dict;
import com.it.service.DictService;
import com.it.util.JsonDateValueProcessor;
import com.it.util.JsonDefaultValueProcessor;

//@Controller
//@RequestMapping(value="dict")
public class DemoController {
//	@Resource(name="dictService")
	private DictService service;
	
	/**
	 * 跳转页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/dictList")
	public ModelAndView list(Model model) {
		return new ModelAndView("system/dict/dictList");
	}
	
	/**
	 * 查询数据并返回json数据
	 * @param po
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/selectList")
	@ResponseBody
	public JSONObject selectList(Dict po, HttpServletRequest request){
		
		PageHelper.startPage(po.getPageNo(), po.getLimit());
		List<Dict> list = service.search(po);
		PageInfo<Dict> list1 = new PageInfo<Dict>(list);

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
	@RequiresPermissions("dict:add")
	@OperLogs(value = "新增字典")
	@ResponseBody
	public int insert(Dict po,HttpServletRequest request) throws Exception{		
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
	@RequiresPermissions(value = "dict:update")
	@OperLogs(value = "编辑字典")
	@ResponseBody
	public int update(Dict po,HttpServletRequest request) throws Exception{
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
	@RequiresPermissions(value = "dict:delete")
	@OperLogs(value = "删除字典")
	@ResponseBody
	public int delete(Dict po, HttpServletRequest request) throws Exception{		
		int res = service.delete(po);
		return res;
	}
	
	/**
	 * 
	 * @Title: searchById  
	 * @Description: TODO  根据id查询
	 * @param @param po
	 * @param @return    参数  
	 * @return List<Dict>    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/selectById")
	@ResponseBody
	public List<Dict> selectById(Dict po){
		List<Dict> list = service.search(po);
		return list;
	}
	
}
