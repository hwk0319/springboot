package com.it.po;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @ClassName: UserInfo  
 * @Description: TODO  用户信息
 * @author Administrator  
 * @date 2019年7月10日  
 *
 */
public class UserInfo extends PagePo{
	private int id;
	private String name;
	private String password;
	private String passwordNew;
	private String status;
	private String phone;
	private String email;
	private Integer sex;
	private Integer roleId;
	private String role;
	private Set<RoleInfo> roles = new HashSet<>();
	private Integer departmentId;
	private Integer departmentPid;
	private String departmentName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<RoleInfo> getRoles() {
		return roles;
	}
	public void setRoles(Set<RoleInfo> roles) {
		this.roles = roles;
	}
	public String getPasswordNew() {
		return passwordNew;
	}
	public void setPasswordNew(String passwordNew) {
		this.passwordNew = passwordNew;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public Integer getDepartmentPid() {
		return departmentPid;
	}
	public void setDepartmentPid(Integer departmentPid) {
		this.departmentPid = departmentPid;
	}
	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", name=" + name + ", password=" + password + ", passwordNew=" + passwordNew
				+ ", status=" + status + ", phone=" + phone + ", email=" + email + ", sex=" + sex + ", roleId=" + roleId
				+ ", role=" + role + ", roles=" + roles + ", departmentId=" + departmentId + ", departmentPid="
				+ departmentPid + ", departmentName=" + departmentName + "]";
	}
	
}
