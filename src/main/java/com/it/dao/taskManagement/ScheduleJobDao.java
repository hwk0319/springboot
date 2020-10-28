package com.it.dao.taskManagement;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.po.ScheduleJob;

@Repository(value="scheduleJobDao")
public interface ScheduleJobDao {
	List<ScheduleJob> selectList(@Param("po") ScheduleJob po);
	
	ScheduleJob selectById(@Param("id") Integer id);
	
    int deleteById(@Param("id") Integer id);

    int insert(@Param("po") ScheduleJob po);

    int updateById(@Param("po") ScheduleJob po);

	int updateStatusById(@Param("po") ScheduleJob po);

	int bacthDelete(@Param("array") String[] array);
}