package com.it.po;

/**
 * 用户角色关联信息
 * @ClassName: UserRoleInfo  
 * @Description: TODO  
 * @author Administrator  
 * @date 2019年7月18日  
 *
 */
public class UserRoleInfo {
	private Integer id;
	private Integer user_id;
	private Integer role_id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getRole_id() {
		return role_id;
	}
	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}
	@Override
	public String toString() {
		return "UserRoleInfo [id=" + id + ", user_id=" + user_id + ", role_id=" + role_id + "]";
	}
	
}
