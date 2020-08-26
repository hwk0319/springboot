package com.it.po;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @ClassName: RoleInfo  
 * @Description: TODO  角色信息
 * @author Administrator  
 * @date 2019年7月10日  
 *
 */
public class RoleInfo extends PagePo{
	private int id;
	private String role;
	private String note;
	private Set<Permission> permissions = new HashSet<>();
	private Integer status;//1-正常 ,0-异常
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Set<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "RoleInfo [id=" + id + ", role=" + role + ", note=" + note + ", permissions=" + permissions + ", status="
				+ status + "]";
	}
	
}
