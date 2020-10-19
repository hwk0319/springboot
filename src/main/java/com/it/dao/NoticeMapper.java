package com.it.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.it.po.Notice;

@Mapper
public interface NoticeMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(Notice record);

    int insertSelective(Notice record);

    Notice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Notice record);

    int updateByPrimaryKey(Notice record);

	List<Notice> search(Notice po);

	int batchDelete(String[] array);
}