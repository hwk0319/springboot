package com.it.service.systemManagement;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.it.dao.systemManagement.DictDao;
import com.it.po.Dict;
import com.it.po.DictInfo;

@Service(value="dictService")
public class DictService {
	@Resource(name = "dictDao")
	private  DictDao dao;
	
	public List<Dict> search(@Param("po") Dict po){
		return dao.search(po);
	}
	
	public int insert(@Param("po") Dict po) {
		return dao.insert(po);
	}	
	
	public int update(@Param("po") Dict po) {
		return dao.update(po);
	}		

	public int delete(@Param("po") Dict po) {
		return dao.delete(po);
	}

	public List<DictInfo> searchDictInfo(DictInfo po) {
		return dao.searchDictInfo(po);
	}

	public int insertDictInfo(DictInfo po) {
		return dao.insertDictInfo(po);
	}

	public int updateDictInfo(DictInfo po) {
		return dao.updateDictInfo(po);
	}

	public int deleteDictInfo(DictInfo po) {
		return dao.deleteDictInfo(po);
	}

	public List<DictInfo> searchDictByType(Dict po) {
		return dao.searchDictByType(po);
	}

	public int deleteDictInfoByIds(String[] array) {
		return dao.deleteDictInfoByIds(array);
	}

}
