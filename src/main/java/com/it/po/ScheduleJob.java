package com.it.po;

/**
 * 
 * @ClassName: ScheduleJob  
 * @Description: TODO  定时任务类
 * @author Administrator  
 * @date 2019年11月7日  
 *
 */
public class ScheduleJob extends PagePo{
    private Integer id;

    private String name;
    
    private String beanName;

    private String methodName;

    private String params;

    private String cronExpression;

    private Integer status;

    private String remark;

    private String createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ScheduleJob [id=" + id + ", name=" + name + ", beanName=" + beanName + ", methodName=" + methodName
				+ ", params=" + params + ", cronExpression=" + cronExpression + ", status=" + status + ", remark="
				+ remark + ", createDate=" + createDate + "]";
	}
	
}