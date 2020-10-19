package com.it.dao.operations;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.it.po.Server;

@Mapper
public interface ServerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Server record);

    int insertSelective(Server record);

    Server selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Server record);

    int updateByPrimaryKey(Server record);
    
    List<Server> select(Server po);

	int batchDelete(String[] array);
}