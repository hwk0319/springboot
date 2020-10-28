package com.it.dao.logManagement;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.po.OperationLogs;

/**
 * 
 * @ClassName: OperationLogs  
 * @Description: TODO  日志dao接口
 * @author Administrator  
 * @date 2019年7月25日  
 *
 */
@Repository(value="operationLogsDao")
public interface OperationLogsDao {
	//查询操作日志
	public List<OperationLogs> search(@Param("po") OperationLogs po);
	//添加操作日志
	public int addOperationLogs(@Param("po") OperationLogs po);
}
