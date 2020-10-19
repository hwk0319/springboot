package com.it.po;

import java.util.Date;

/**
 * 
 * @ClassName: notice  
 * @Description: TODO  通知公告类
 * @author Administrator  
 * @date 2020年9月23日  
 *
 */
public class Notice extends PagePo{
    private Integer id;//id

    private String title;//标题

    private Integer type;//类型 1=通知，2=公告

    private String content;//类容

    private Integer status;//状态 1=正常，2=关闭
    
    private String creator;//创建者
    
    private String createDate;//创建时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "Notice [id=" + id + ", title=" + title + ", type=" + type + ", content=" + content + ", status="
				+ status + ", creator=" + creator + ", createDate=" + createDate + "]";
	}
}