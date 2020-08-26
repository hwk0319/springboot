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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.aspect.OperLogs;
import com.it.po.Dict;
import com.it.po.DictInfo;
import com.it.service.DictService;
import com.it.util.JsonDateValueProcessor;
import com.it.util.JsonDefaultValueProcessor;
import com.it.util.Result;
import com.it.util.ReturnCode;

@Controller
@RequestMapping(value="dict")
public class DictController {
	@Resource(name="dictService")
	private DictService service;
	
	/**
	 * 跳转数据字典页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/dictList")
	public ModelAndView list(Model model) {
		return new ModelAndView("system/dict/dictList");
	}
	
	/**
	 * 查询日志数据并返回json数据
	 * @param po
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/search")
	@ResponseBody
	public JSONObject search(Dict po, HttpServletRequest request){
		
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
	
	@RequestMapping(value="/search1")
	@ResponseBody
	public Result search1(Dict po, HttpServletRequest request){
		
		PageHelper.startPage(po.getPageNo(), po.getLimit());
		List<Dict> list = service.search(po);
		
		return Result.markSuccess(list);
	}
	
	/**
	 * 
	 * @Title: insert  
	 * @Description: TODO  新增字典数据
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
	 * @Description: TODO  编辑字典
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
	 * @Description: TODO  删除字典
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
	 * 查询数据并返回json数据
	 * @param po
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/searchDictInfo")
	@ResponseBody
	public JSONObject searchDictInfo(DictInfo po, HttpServletRequest request){
		
		PageHelper.startPage(po.getPageNo(), po.getLimit());
		List<DictInfo> list = service.searchDictInfo(po);
		PageInfo<DictInfo> list1 = new PageInfo<DictInfo>(list);

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
	 * @Title: insertDictInfo  
	 * @Description: TODO  新增字典详情数据
	 * @param @param po
	 * @param @param request
	 * @param @return
	 * @param @throws Exception    参数  
	 * @return int    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/insertDictInfo")
	@RequiresPermissions("dict:add")
	@OperLogs(value = "新增字典详情")
	@ResponseBody
	public int insertDictInfo(DictInfo po,HttpServletRequest request) throws Exception{		
		int res = service.insertDictInfo(po);
		return res;
	}
	
	/**
	 * 
	 * @Title: updateDictInfo  
	 * @Description: TODO  编辑字典项详情
	 * @param @param po
	 * @param @param request
	 * @param @return
	 * @param @throws Exception    参数  
	 * @return int    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/updateDictInfo")
	@RequiresPermissions(value = "dict:update")
	@OperLogs(value = "编辑字典详情")
	@ResponseBody
	public int updateDictInfo(DictInfo po,HttpServletRequest request) throws Exception{
		int res = 0;
		try {
			res = service.updateDictInfo(po);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 
	 * @Title: deleteDictInfo  
	 * @Description: TODO  删除字典详情
	 * @param @param po
	 * @param @param request
	 * @param @return    参数  
	 * @return int    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/deleteDictInfo")
	@RequiresPermissions(value = "dict:delete")
	@OperLogs(value = "删除字典项")
	@ResponseBody
	public int deleteDictInfo(DictInfo po, HttpServletRequest request) throws Exception{		
		int res = service.deleteDictInfo(po);
		return res;
	}
	
	/**
	 * 
	 * @Title: deleteDictInfoByIds  
	 * @Description: TODO  多条数据删除
	 * @param @param po
	 * @param @param request
	 * @param @return
	 * @param @throws Exception    参数  
	 * @return int    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/deleteDictInfoByIds")
	@RequiresPermissions(value = "dict:delete")
	@OperLogs(value = "删除字典项")
	@ResponseBody
	public int deleteDictInfoByIds(DictInfo po, HttpServletRequest request) throws Exception{		
		String []array = po.getIds().split(",");
		int res = 0;
		try {
			res = service.deleteDictInfoByIds(array);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 
	 * @Title: searchById  
	 * @Description: TODO  根据id查询字典数据
	 * @param @param po
	 * @param @return    参数  
	 * @return List<Dict>    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/searchDictById")
	@ResponseBody
	public List<Dict> searchById(Dict po){
		List<Dict> list = service.search(po);
		return list;
	}
	
	/**
	 * 
	 * @Title: searchDictByType  
	 * @Description: TODO  根据数据字典类型查询字典值
	 * @param @param po
	 * @param @return    参数  
	 * @return List<Dict>    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/searchDictByType")
	@ResponseBody
	public List<DictInfo> searchDictByType(Dict po){
		List<DictInfo> list = service.searchDictByType(po);
		return list;
	}
	
	/**
	 * 
	 * @Title: loadDictInfoPage  
	 * @Description: TODO  加载编辑字典项页面，带参数
	 * @param @param path
	 * @param @param dictId
	 * @param @param dictInfoId
	 * @param @param model
	 * @param @return    参数  
	 * @return ModelAndView    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/loadDictInfoPage")
	public ModelAndView loadDictInfoPage(@RequestParam("path") String path, @RequestParam("dictId") String dictId, @RequestParam("dictInfoId") String dictInfoId, Model model) {
		model.addAttribute("dictId", dictId);
		model.addAttribute("dictInfoId", dictInfoId);
		return new ModelAndView(path);
	}
}
