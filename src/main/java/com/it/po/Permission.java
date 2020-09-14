package com.it.po;

import java.util.List;

/**
 * 
 * @ClassName: permission  
 * @Description: TODO  权限资源信息
 * @author Administrator  
 * @date 2019年7月10日  
 *
 */
public class Permission {
	private Integer id;
	private String name;
	private Integer pid;
	private String permission;
	private String url;
	private Integer type;
	private String status;
	private String image;
	private Integer sort;
	private List<Permission> childern;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public List<Permission> getChildern() {
		return childern;
	}
	public void setChildern(List<Permission> childern) {
		this.childern = childern;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	@Override
	public String toString() {
		return "Permission [id=" + id + ", name=" + name + ", pid=" + pid + ", permission=" + permission + ", url="
				+ url + ", type=" + type + ", status=" + status + ", image=" + image + ", childern=" + childern + ", sort=" + sort + "]";
	}
}
