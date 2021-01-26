package com.it.po;

/**
 * 
 * @author Administrator
 * @date 2017年5月22日
 * @Description 分页po,分页参数
 *
 */
public class PagePo {
	
	private int pageNo;//页码
	private int limit;//数量

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
}
