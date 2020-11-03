package com.it.controller.systemTools;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.aspect.OperLogs;
import com.it.po.ImageInfo;
import com.it.po.UserInfo;
import com.it.service.systemTools.ImageService;
import com.it.util.FileUtil;
import com.it.util.JsonDateValueProcessor;
import com.it.util.JsonDefaultValueProcessor;
import com.it.util.Result;
import com.it.util.Util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@RestController
@RequestMapping(value="image")
public class ImageController {
	
	private static final Logger logger = LoggerFactory.getLogger(ImageController.class);
	
	@Resource(name="imageService")
	private ImageService service;
	@Value("${file.staticAccessPath}")
	private String staticAccessPath;
	@Value("${file.uploadPath}")
	private String uploadPath;
	
	/**
	 * 跳转到图片页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/imageList")
	public ModelAndView list(Model model) {
		return new ModelAndView("systemTools/image/imageList");
	}
	
	/**
	 * 查询用户列表
	 * @param po
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/selectList")
	public Result selectList(ImageInfo po, HttpServletRequest request){
		PageHelper.startPage(po.getPageNo(), po.getLimit());
		List<ImageInfo> list = service.selectList(po);
		return Result.success(list, list.size());
	}
	
	/**
	 * 删除图片
	 * @param po
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/delete")
	@OperLogs(value = "删除图片")
	public int delete(ImageInfo po, HttpServletRequest request){		
		int res = service.delete(po.getId());
		return res;
	}
	
	/**
	 * 
	 * @Title: bacthDeleteUser  
	 * @Description: TODO  批量删除
	 * @param @param ids
	 * @param @param request
	 * @param @return    参数  
	 * @return int    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/bacthDelete")
	@OperLogs(value = "批量删除")
	public int bacthDeleteUser(@RequestParam(name = "ids") String ids, HttpServletRequest request){	
		String[] array = ids.split(",");
		int res = 0;
		try {
			res = service.bacthDelete(array);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return res;
	}
	
	/**
	 * 
	 * @Title: selectById  
	 * @Description: TODO  根据id查询
	 * @param @param po
	 * @param @return    参数  
	 * @return ImageInfo    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/selectById")
	public ImageInfo selectById(ImageInfo po) {
		ImageInfo imageInfo = service.selectById(po.getId());
		return imageInfo;
	}
	
	/**
	 * @throws Exception 
	 * 
	 * @Title: Upload  
	 * @Description: TODO  上传图片
	 * @param @param file
	 * @param @return    参数  
	 * @return String    返回类型  
	 * @throws
	 */
	@RequestMapping(value = "/upload")
	public Map<String, Object> Upload(@RequestParam("file_data") MultipartFile file) throws Exception {
		int res = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		if(!file.isEmpty()) {
			// 获取文件名称,包含后缀			
			String fileName = file.getOriginalFilename();
			long fileSize = file.getSize()/1024;
//			String fileType = file.getContentType();
			int fileWidth = 0;
			int fileHeight = 0;
			try {
				BufferedImage image = ImageIO.read(file.getInputStream());
				if (image != null) {//如果image=null 表示上传的不是图片格式
					fileWidth = image.getWidth();//获取图片宽度，单位px
					fileHeight = image.getHeight();//获取图片高度，单位px
				}else {
					map.put("code", res);
					map.put("msg", "上传失败！");
					return map;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			//上传文件
			FileUtil.uploadFile(file.getBytes(), uploadPath, fileName);
			// 创建对应的实体类，将以下路径进行添加，然后通过数据库操作方法写入			
			UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
			ImageInfo po = new ImageInfo();
			po.setName(fileName);
			po.setImagesize(String.valueOf(fileSize));
			po.setWidth(String.valueOf(fileWidth));
			po.setHeigth(String.valueOf(fileHeight));
			po.setUsername(user.getName());
			po.setCreatedate(Util.getFormatDate());
			po.setUrl(staticAccessPath.replace("**", "")+fileName);
			res = service.insert(po);
		}
		map.put("code", res);
		map.put("msg", "上传成功！");
		return map;
	}
	

}
