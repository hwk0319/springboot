package com.it.dao.systemTools;

import com.it.po.MailTools;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository(value="mailToolsMapper")
public interface MailToolsMapper {
    int deleteByPrimaryKey(@Param("id") int id);

    int insert(@Param("po") MailTools po);

    int insertSelective(@Param("po")MailTools po);

    MailTools selectByPrimaryKey(@Param("id") int id);

    int updateByPrimaryKeySelective(@Param("po")MailTools po);

    int updateByPrimaryKey(@Param("po")MailTools po);

	List<MailTools> search();
}