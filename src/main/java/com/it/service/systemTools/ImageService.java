package com.it.service.systemTools;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.it.dao.systemTools.ImageInfoDao;
import com.it.po.ImageInfo;

@Service(value="imageService")
public class ImageService {
	@Resource(name="imageInfoDao")
	private ImageInfoDao dao;
	
	//查询集合
	public List<ImageInfo> selectList(ImageInfo po) {
		return dao.selectList(po);
	}
	
	//根据id查询
	public ImageInfo selectById(int id) {
		return dao.selectById(id);
	}

	// 删除
	public int delete(int id) {
		int res = dao.deleteById(id);
		return res;
	}

	// 新增
	public int insert(ImageInfo po) {
		int res = dao.insert(po);
		return res;
	}

	//更新
	public int update(ImageInfo po) {
		int res = dao.update(po);
		return res;
	}

	public int bacthDelete(String[] array) {
		return dao.bacthDelete(array);
	}

}
