package com.it.po;

/**
 * 
 * @ClassName: ImageInfo  
 * @Description: TODO  图片管理
 * @author Administrator  
 * @date 2019年11月1日  
 *
 */
public class ImageInfo extends PagePo{
    private Integer id;

    private String name;

    private String imagesize;

    private String heigth;

    private String width;

    private String username;

    private String createdate;
    
    private String url;

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

    public String getImagesize() {
        return imagesize;
    }

    public void setImagesize(String imagesize) {
        this.imagesize = imagesize;
    }

    public String getHeigth() {
        return heigth;
    }

    public void setHeigth(String heigth) {
        this.heigth = heigth;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

	@Override
	public String toString() {
		return "ImageInfo [id=" + id + ", name=" + name + ", imagesize=" + imagesize + ", heigth=" + heigth + ", width="
				+ width + ", username=" + username + ", createdate=" + createdate + "]";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
    
}