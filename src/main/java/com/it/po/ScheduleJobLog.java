package com.it.po;

/**
 * 
 * @ClassName: ScheduleJobLog  
 * @Description: TODO  定时任务日志
 * @author Administrator  
 * @date 2019年11月7日  
 *
 */
public class ScheduleJobLog extends PagePo{
    private Integer id;

    private Integer jobId;

    private String name;
    
    private String beanName;

    private String methodName;

    private String params;

    private Integer status;

    private String error;

    private Integer times;

    private String createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
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
		return "ScheduleJobLog [id=" + id + ", jobId=" + jobId + ", name=" + name + ", beanName=" + beanName
				+ ", methodName=" + methodName + ", params=" + params + ", status=" + status + ", error=" + error
				+ ", times=" + times + ", createDate=" + createDate + "]";
	}
}