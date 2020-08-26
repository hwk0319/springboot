package com.it.dao;

import com.it.po.ScheduleJobLog;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleJobLogDao {
	
    List<ScheduleJobLog> selectList(@Param("po") ScheduleJobLog po);
	
    int deleteById(@Param("id") Integer id);

    int insert(@Param("po") ScheduleJobLog po);

    ScheduleJobLog selectById(@Param("id") Integer id);

    int updateById(@Param("po") ScheduleJobLog po);
}