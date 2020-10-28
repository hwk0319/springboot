package com.it.service.logManagement;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.it.dao.logManagement.OperationLogsDao;
import com.it.po.OperationLogs;
/**
 * 
 * @ClassName: OperationLogsService  
 * @Description: TODO  操作日志service类
 * @author Administrator  
 * @date 2019年7月25日  
 *
 */
@Service(value = "operationLogsService")
public class OperationLogsService {
	
	@Resource(name = "operationLogsDao")
	private OperationLogsDao dao;
	
	public List<OperationLogs> search(OperationLogs po){
		return dao.search(po);
	}
	
	public int addOperationLogs(OperationLogs po) {
		return dao.addOperationLogs(po);
	}
}
