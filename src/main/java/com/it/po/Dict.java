package com.it.po;

/**
 * 
 * @author 黄文凯
 * @date 2019年9月7日16:50:49
 * @Description 数据字典
 *
 */
public class Dict extends PagePo{
	private Integer id;//编号
	private String name;//名称
	private String type;//类型
	private Integer status;//1-在用 ,0-已删除
	private String createDate;//创建日期
	private String remark;//备注
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "Dict [id=" + id + ", type=" + type + ", name=" + name + ", status=" + status + ", createDate="
				+ createDate + ", remark=" + remark + "]";
	}
	
}
