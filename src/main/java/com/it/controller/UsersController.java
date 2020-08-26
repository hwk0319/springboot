package com.it.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.aspect.OperLogs;
import com.it.po.UserInfo;
import com.it.po.UserRoleInfo;
import com.it.service.UserService;
import com.it.util.ExcelUtil;
import com.it.util.JsonDateValueProcessor;
import com.it.util.JsonDefaultValueProcessor;
import com.it.util.Result;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


@RestController
@RequestMapping(value="user")
public class UsersController {
	@Resource(name="userService")
	private UserService service;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 跳转到user页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/userList")
	public ModelAndView list(Model model) {
		return new ModelAndView("system/user/userList");
	}
	/**
	 * 跳转到userAdd页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/userAdd")
	public ModelAndView Add(Model model) {
		return new ModelAndView("system/user/userAdd");
	}
	/**
	 * 查询用户
	 * @param po
	 * @return
	 */
	@RequestMapping(value="/search")
	public List<UserInfo> search(UserInfo po){
		List<UserInfo> user = new ArrayList<>();
		try {
			user = service.search(po);
		} catch (Exception e) {
		}
		return user;
	}
	
	/**
	 * 查询用户列表
	 * @param po
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/search2")
	public JSONObject search1(UserInfo po, HttpServletRequest request){
		
		PageHelper.startPage(po.getPageNo(), po.getLimit());
		List<UserInfo> list = service.search(po);
		PageInfo<UserInfo> list1 = new PageInfo<UserInfo>(list);

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
	 * @Title: search3  
	 * @Description: TODO  查询用户
	 * @param @param po
	 * @param @param request
	 * @param @return    参数  
	 * @return Result    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/search3")
	public Result search3(UserInfo po, HttpServletRequest request) {
		PageHelper.startPage(po.getPageNo(), po.getLimit());
		List<UserInfo> list = service.search(po);
		PageInfo<UserInfo> list1 = new PageInfo<UserInfo>(list);
		return Result.markSuccessTotal(list, String.valueOf(list1.getTotal()));
	}
	
	/**
	 * 保存用户
	 * @author ydd
	 * @param name
	 * @param userId
	 * @param age
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/insert")
	@RequiresPermissions("userinfo:add")
	@OperLogs(value = "新增用户")
	public int insert(UserInfo po,HttpServletRequest request) throws Exception{		
		po.setStatus("1");
		int res = service.insert(po);
		if(po.getRoleId() != null) {
			UserRoleInfo ur = new UserRoleInfo();
			ur.setUser_id(po.getId());
			ur.setRole_id(po.getRoleId());
			res = service.accredit(ur);
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
//	@RequiresPermissions(value = "userinfo:edit")
	@OperLogs(value = "编辑用户")
	public int update(UserInfo po,HttpServletRequest request) throws Exception{
		int res = service.update(po, request);
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
	@OperLogs(value = "注销用户")
	public int delete(UserInfo po,HttpServletRequest request) throws Exception{		
		int res =  service.delete(po, request);
		return res;
	}
	
	/**
	 * 用户授权
	 * @param po
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/accredit")
	@OperLogs(value = "用户授权")
	public int accredit(UserRoleInfo po) throws Exception{	
		//查询是否已经授权，如果是，进行更新；如果否，进行添加
		List<UserRoleInfo> list = service.searchUserRoleInfo(po);
		int res = 0;
		if(list == null || list.size() == 0) {
			res = service.accredit(po);
		}else {
			res = service.updateAccredit(po);
		}
		return res;
	}
	
	/**
	 * 检查用户名是否重复
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/checkUsername")
	public Map<String, Boolean> checkUsername(String username){
		UserInfo userInfo =	service.findUserByName(username);
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		if(userInfo != null) {
			map.put("valid", false);
		}else {
			map.put("valid", true);
		}
		return map;
	}
	
	/**
	 * 删除用户
	 * @param po
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/deleteUser")
	@OperLogs(value = "删除用户")
	public int deleteUser(UserInfo po, HttpServletRequest request){		
		int res = service.deleteUser(po);
		return res;
	}
	
	/**
	 * 
	 * @Title: bacthDeleteUser  
	 * @Description: TODO  批量删除用户
	 * @param @param po
	 * @param @param request
	 * @param @return    参数  
	 * @return int    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/bacthDeleteUser")
	@OperLogs(value = "批量删除用户")
	public int bacthDeleteUser(@RequestParam(name = "ids") String ids, HttpServletRequest request){	
		String[] array = ids.split(",");
		int res = 0;
		try {
			res = service.bacthDeleteUser(array);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return res;
	}
	
	/**
	 * 修改密码
	 * @param po
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/updatePass")
	@OperLogs(value = "修改密码")
	public int updatePass(UserInfo po,HttpServletRequest request) {	
		//原密码
		String pass_old = po.getPassword();
		//查询数据库密码
		List<UserInfo> users = service.search(po);
		//数据库密码
		String dbpass = users.get(0).getPassword();
		//判断密码是否一致
		if(!dbpass.equals(pass_old)){
			  return 0;
		}
		//修改密码
		po.setPassword(po.getPasswordNew());
		int res = service.updatePass(po);
		return res;
	}
	
	/**
	 * 
	 * @Title: exportExcel  
	 * @Description: TODO  导出Excel
	 * @param @param response
	 * @param @param po    参数  
	 * @return void    返回类型  
	 * @throws
	 */
	@RequestMapping(value="exportExcel")
	@OperLogs(value="导出Excel")
	public void exportExcel(HttpServletResponse response, UserInfo po) throws Exception {
		List<UserInfo> classmateList = service.search(po);
//		List<List<String>> list1 = new ArrayList<List<String>>();
		
//		for (int i = 0; i < list.size(); i++) {
//			List<String> list2 = new ArrayList<String>();
//			list2.add(list.get(i).getName());
//			list2.add(list.get(i).getRole());
//			list2.add(list.get(i).getPhone());
//			list2.add(list.get(i).getEmail());
//			list2.add(list.get(i).getSex().toString());
//			list2.add(list.get(i).getStatus());
//			list1.add(list2);
//		}
		
//		String[] array = {"用户名","角色名称","手机号","邮箱","性别","状态"};
//		List<String> columnList = Arrays.asList(array);
//		String fileName = "用户信息表";
//		ExcelUtil.uploadExcel(response, fileName, columnList, list1);
		HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("用户信息表");

//        List<Teacher> classmateList = teacherservice.teacherinfor();

        String fileName = "用户信息表"  + ".xls";//设置要导出的文件的名字
        //新增数据行，并且设置单元格数据

        int rowNum = 1;

        String[] headers = {"用户名","角色名称","手机号","邮箱","性别","状态"};
        //headers表示excel表中第一行的表头

        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头

        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        //在表中存放查询到的数据放入对应的列
        for (UserInfo teacher : classmateList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(teacher.getName());
            row1.createCell(1).setCellValue(teacher.getRole());
            row1.createCell(2).setCellValue(teacher.getPhone());
            row1.createCell(3).setCellValue(teacher.getEmail());
            row1.createCell(4).setCellValue(teacher.getSex());
            row1.createCell(5).setCellValue(teacher.getStatus());
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        try {
			response.flushBuffer();
			workbook.write(response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
