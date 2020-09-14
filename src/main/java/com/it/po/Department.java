package com.it.po;

import java.util.Date;

/**
 * 
 * @ClassName: Department  
 * @Description: TODO  部门
 * @author Administrator  
 * @date 2020年7月9日  
 *
 */
public class Department{

	//主键
	private Integer id;
	//部门编号
	private String departmentNum;
	//名称
	private String name;
	//负责任人
	private String personInCharge;
	//联系电话
	private String phone;
	//排序
	private Integer sort;
	//状态
	private Integer status;
	//创建时间
	private Date create_time;
	//创建人
	private String creator;
	//更新时间
	private Date update_time;
	//跟新人
	private String updater;
	//上级部门id
	private Integer pid;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDepartmentNum() {
		return departmentNum;
	}
	public void setDepartmentNum(String departmentNum) {
		this.departmentNum = departmentNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPersonInCharge() {
		return personInCharge;
	}
	public void setPersonInCharge(String personInCharge) {
		this.personInCharge = personInCharge;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", departmentNum=" + departmentNum + ", name=" + name + ", personInCharge=" + personInCharge
				+ ", phone=" + phone + ", sort=" + sort + ", status=" + status + ", create_time=" + create_time
				+ ", creator=" + creator + ", update_time=" + update_time + ", updater=" + updater + ", pid="
				+ pid + "]";
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	
}
