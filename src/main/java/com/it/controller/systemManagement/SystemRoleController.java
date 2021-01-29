package com.it.controller.systemManagement;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.it.aspect.NoRepeatSubmit;
import com.it.aspect.OperLogs;
import com.it.po.RoleInfo;
import com.it.service.systemManagement.SystemRoleService;
import com.it.util.Result;

@Controller
@RequestMapping(value="/role")
public class SystemRoleController{
	
	private static final Logger logger = LoggerFactory.getLogger(SystemRoleController.class);
	
	@Resource(name="systemRoleService")
	private SystemRoleService service;
	/**
	 * 跳转到角色管理页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/roleList")
	@RequiresPermissions(value="roleInfo:view")
	public ModelAndView list(Model model) {
		return new ModelAndView("system/role/roleList");
	}
	
	/**
	 * 角色列表查询
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/sysRoleSearch")
	@RequiresPermissions(value="roleInfo:view")
	public List<RoleInfo> sysRoleSearch(HttpServletRequest request, RoleInfo po){
		List<RoleInfo> list = service.sysRoleSearch(po);
		return list;
	}
	/**
	 * 增加角色
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/addRole")
	@RequiresPermissions(value="roleinfo:add")
	@OperLogs(value = "增加角色")
	@Transactional
	@NoRepeatSubmit
	public Result addRole(RoleInfo po, HttpServletRequest request) throws Exception{
		int res = service.addRole(po);
		String idArr = request.getParameter("idArr");
		String []arr = idArr == "" ? null : idArr.split(",");
		if(arr != null && arr.length > 0) {
			res = service.addRolePermissions(arr, po.getId());
		}
		return Result.success(res);
	}
	/**
	 * 修改角色信息
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/updateRole")
	@RequiresPermissions(value="roleinfo:edit")
	@OperLogs(value = "修改角色")
	@Transactional
	public int updateRole(RoleInfo po,HttpServletRequest request) throws Exception{
		//编辑角色
		int res = service.updateRole(po);
		//编辑角色权限关联信息，先删除原来的数据，然后重新添加数据
		res = service.deleteRolePermission(po);
		String idArr = request.getParameter("idArr");
		String []arr = idArr == "" ? null : idArr.split(",");
		if(arr != null && arr.length > 0) {
			res = service.addRolePermissions(arr, po.getId());
		}
		return res;
	}
	
	/**
	 * 
	 * @Title: updateRoleAccredit  
	 * @Description: TODO  角色授权
	 * @param @param po
	 * @param @param request
	 * @param @return
	 * @param @throws Exception    参数  
	 * @return int    返回类型  
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/updateRoleAccredit")
	@RequiresPermissions(value="roleinfo:accredit")
	@OperLogs(value = "修改角色权限")
	@Transactional
	public int updateRoleAccredit(RoleInfo po, HttpServletRequest request) throws Exception{
		//编辑角色权限关联信息，先删除原来的数据，然后重新添加数据
		int res = service.deleteRolePermission(po);
		String idArr = request.getParameter("idArr");
		String []arr = idArr == "" ? null : idArr.split(",");
		if(arr != null && arr.length > 0) {
			res = service.addRolePermissions(arr, po.getId());
		}
		return res;
	}
	
	/**
	 * 删除角色信息
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/deleteRole")
	@RequiresPermissions(value="roleinfo:delete")
	@OperLogs(value = "删除角色")
	@Transactional
	public int deleteRole(RoleInfo po) throws Exception{
		//删除角色信息
		int res = service.deleteRole(po);
		//删除角色权限关联信息
		res = service.deleteRolePermission(po);
		return res;
	}
	
	/**
	 * 
	 * @Title: batchDeleteRole  
	 * @Description: TODO  批量删除角色
	 * @param @param ids
	 * @param @param request
	 * @param @return    参数  
	 * @return int    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/batchDeleteRole")
	@RequiresPermissions(value="roleinfo:delete")
	@OperLogs(value = "批量删除角色")
	public int batchDeleteRole(@RequestParam(name = "ids") String ids, HttpServletRequest request){	
		String[] array = ids.split(",");
		int res = 0;
		try {
			res = service.batchDeleteRole(array);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return res;
	}
	
	/**
	 * 角色列表查询，使用PageHelper分页插件分页查询
	 * @param request
	 * @param po
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/selectList")
	@RequiresPermissions(value="roleInfo:view")
	public Result selectList(RoleInfo po, HttpServletRequest request){
		PageHelper.startPage(po.getPageNo(), po.getLimit());
		List<RoleInfo> list = service.sysRoleSearch(po);
		return Result.success(list, list.size());
	}
}
