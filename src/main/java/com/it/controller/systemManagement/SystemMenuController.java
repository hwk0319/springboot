package com.it.controller.systemManagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.it.aspect.OperLogs;
import com.it.po.Permission;
import com.it.po.RolePermission;
import com.it.po.Treebs;
import com.it.po.UserInfo;
import com.it.service.systemManagement.SystemMenuService;
import com.it.util.JsonDateValueProcessor;
import com.it.util.JsonDefaultValueProcessor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("/menu")
public class SystemMenuController {
	@Resource
	private SystemMenuService menuservice;
	
	/**
	 * 跳转菜单管理页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/menuList")
	public ModelAndView menuList(ModelAndView model) {
		return new ModelAndView("system/menu/menuList");
	}

	/**
	 * 
	 * @Title: findMenuByUser  
	 * @Description: TODO  根据用户加载菜单
	 * @param @param request
	 * @param @return    参数  
	 * @return List<Permission>    返回类型  
	 * @throws
	 */
	@RequestMapping("/menu")
	@ResponseBody
	public List<Permission> findMenuByUser(HttpServletRequest request) {
		Object obj = SecurityUtils.getSubject().getPrincipal();
		UserInfo user = (UserInfo) obj;
		List<Permission> list = menuservice.findMenuByUser(user);
		List<Permission> list1 = new ArrayList<Permission>();
		for (int i = 0; i < list.size(); i++) {
			List<Permission> list2 = new ArrayList<Permission>();
			if(list.get(i).getPid() == 0) {
				for (int j = 0; j < list.size(); j++) {
					if(list.get(j).getPid() == list.get(i).getId()) {
						Permission p = list.get(j);
						list2.add(p);
					}
				}
				Permission p1 = list.get(i);
				p1.setChildern(list2);
				list1.add(p1);
			}
		}
		return list1;
	}
	
	/**
	 * 
	 * @Title: searchMenuById  
	 * @Description: TODO  根据id查询菜单
	 * @param @param po
	 * @param @return    参数  
	 * @return List<Permission>    返回类型  
	 * @throws
	 */
	@RequestMapping("/searchMenuById")
	@ResponseBody
	public List<Permission> searchMenuById(Permission po){
		List<Permission> list = menuservice.searchMenuById(po);
		return list;
	}

	/**
	 * 
	 * @Title: findmenubyPid  
	 * @Description: TODO  根据父级菜单id查询子菜单
	 * @param @param parentcode
	 * @param @param request
	 * @param @return    参数  
	 * @return List<SystemMenu>    返回类型  
	 * @throws
	 */
	@RequestMapping("/findmenubyid")
	@ResponseBody
	public List<Permission> findMenuByid(int id, HttpServletRequest request) {
		List<Permission> list = menuservice.findMenuByid(id);
		return list;
	}
	
	/**
	 * 查询菜单数据
	 * @param po
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/search")
	@ResponseBody
	public JSONObject search1(Permission po, HttpServletRequest request){
		
		List<Permission> list = menuservice.search(po);

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
	
	/**
	 * 跳转到menuAdd页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/menuAdd")
	@ResponseBody
	public ModelAndView menuAdd(ModelAndView model) {
		return new ModelAndView("system/menu/menuAdd");
	}
	
	
	/**
	 * 添加菜单
	 * @param po
	 * @return
	 */
	@RequestMapping(value="/addMenu")	
	@OperLogs(value = "添加菜单")
	@ResponseBody
	public int addMenu(Permission po){
		if(po.getPid() == null) {
			po.setPid(0);
		}
		po.setStatus("1");
		int res = menuservice.addMenu(po);
		return res;
	}
	
	/**
	 * 修改菜单
	 * @param po
	 * @return
	 */
	@RequestMapping(value="/updateMenu")
	@OperLogs(value = "编辑菜单")
	@ResponseBody
	public int updateMenu(Permission po){
		return	menuservice.updateMenu(po);
	}
	
	/**
	 * 删除菜单
	 * @param po
	 * @return
	 */
	@RequestMapping(value="/deleteMenu")
	@OperLogs(value = "删除菜单")
	@ResponseBody
	public int deleteMenu(Permission po){
		return	menuservice.deleteMenu(po);
	}
	
	/**
	 * 添加菜单，获取菜单树1，文本框下拉菜单树
	 * @param SystemMenu
	 * @param req
	 * @return
	 */
	@RequestMapping("/getMenu1")
	@ResponseBody
	public JSONObject getMenu1(Permission po, HttpServletRequest request){
		List<Permission> list = menuservice.search(po);
		List<Treebs> list2 = new ArrayList<Treebs>();
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getPid() == 0) {
				Treebs t = new Treebs();
				t.setId(list.get(i).getId());
				t.setText(list.get(i).getName());
				List<Treebs> list1 = new ArrayList<Treebs>();
				for (int j = 0; j < list.size(); j++) {
					if(list.get(i).getId() == list.get(j).getPid()) {
						Treebs t1 = new Treebs();
						t1.setId(list.get(j).getId());
						t1.setText(list.get(j).getName());
						list1.add(t1);
					}
				}
				t.setNodes(list1);
				list2.add(t);
			}
		}
		//转成JSONArray
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());//时间格式转换
		config.registerDefaultValueProcessor(Integer.class, new JsonDefaultValueProcessor());//数据格式转换
		JSONArray json = JSONArray.fromObject(list2,config); 
		
		for (int i = 0; i < json.size(); i++) {
			JSONObject jb = json.getJSONObject(i);
			JSONArray jarr = jb.getJSONArray("nodes");
			if(jarr.isEmpty()) {
				jb.discard("nodes");
			}else {
				for (int j = 0; j < jarr.size(); j++) {
					JSONObject jb1 = jarr.getJSONObject(j);
					JSONArray jarr1 = jb1.getJSONArray("nodes");
					if(jarr1.isEmpty()) {
						jb1.discard("nodes");
					}
				}
			}
		}
		
		JSONObject jsonObject = new JSONObject();  //创建Json对象
		jsonObject.put("text", "后台管理系统");
		jsonObject.put("nodes", json);//json数据
		return jsonObject;
	}
	
	
	/**
	 * 
	 * @Title: getRolePermission  
	 * @Description: TODO  查询角色权限关联数据
	 * @param @param po
	 * @param @return    参数  
	 * @return List<RolePermission>    返回类型  
	 * @throws
	 */
	@RequestMapping("/getRolePermission")
	@ResponseBody
	public List<RolePermission> getRolePermission(RolePermission po){
		List<RolePermission> list = menuservice.searchRolePermission(po);
		return list;
	}
	
	/**
	 * 
	 * @Title: getMenuTree  
	 * @Description: TODO  获取菜单树
	 * @param @param po
	 * @param @return    参数  
	 * @return Map<String,Object>    返回类型  
	 * @throws
	 */
	@RequestMapping("/getMenuTree")
	@ResponseBody
	public Map<String, Object> getMenuTree(Permission po){
		List<Permission> list = menuservice.searchMenuTree(po);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("menulists", list);
		return map;
	}
	
	/**
	 * 
	 * @Title: getMenuTree  
	 * @Description: TODO  获取菜单树
	 * @param @param po
	 * @param @return    参数  
	 * @return Map<String,Object>    返回类型  
	 * @throws
	 */
	@RequestMapping("/getMenuTree1")
	@ResponseBody
	public Map<String, Object> getMenuTree1(Permission po){
		List<Permission> list = menuservice.searchMenuTree1(po);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("menulists", list);
		return map;
	}
	
}
