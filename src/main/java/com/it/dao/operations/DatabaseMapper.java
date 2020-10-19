package com.it.dao.operations;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.it.po.Database;

@Mapper
public interface DatabaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Database record);

    int insertSelective(Database record);

    Database selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Database record);

    int updateByPrimaryKey(Database record);
    
    List<Database> select(Database po);

	int batchDelete(String[] array);
}