package com.it.po;

/**
 * 
 * @author 黄文凯
 * @date 2019年9月7日16:50:49
 * @Description 数据字典详情
 *
 */
public class DictInfo extends PagePo{
	private Integer id;//编号
	private Integer dictid;//dictid编号
	private String name;//名称
	private String value;//值
	private Integer sort;//排序
	private Integer status;//1-在用 ,0-已删除
	private String createDate;//创建日期
	private String remark;//备注
	private String ids;//多个id
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDictid() {
		return dictid;
	}
	public void setDictid(Integer dictid) {
		this.dictid = dictid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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
		return "DictInfo [id=" + id + ", dictid=" + dictid + ", name=" + name + ", value=" + value + ", sort=" + sort
				+ ", status=" + status + ", createDate=" + createDate + ", remark=" + remark + "]";
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	
}
