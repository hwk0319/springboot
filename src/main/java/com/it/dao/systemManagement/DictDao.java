package com.it.dao.systemManagement;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.po.Dict;
import com.it.po.DictInfo;

@Repository(value="dictDao")
public interface DictDao{
	public List<Dict> search(@Param("po") Dict po);	
	public int insert(@Param("po") Dict po);
	public int update(@Param("po") Dict po);
	public int delete(@Param("po") Dict po);
	public List<DictInfo> searchDictInfo(@Param("po") DictInfo po);
	public int insertDictInfo(@Param("po") DictInfo po);
	public int updateDictInfo(@Param("po") DictInfo po);
	public int deleteDictInfo(@Param("po") DictInfo po);
	public List<DictInfo> searchDictByType(@Param("po") Dict po);
	public int deleteDictInfoByIds(@Param("array") String[] array);
	
}
