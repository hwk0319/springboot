package com.it.po;

/**
 * 
 * @ClassName: Server  
 * @Description: TODO  服务器类
 * @author Administrator  
 * @date 2020年9月28日  
 *
 */
public class Server extends PagePo{
    private Integer id;

    private String name;

    private String account;

    private String password;

    private Integer port;

    private String ip;

    private String creator;

    private String createdate;

    private String updator;

    private String updatedate;

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

	@Override
	public String toString() {
		return "Server [id=" + id + ", name=" + name + ", account=" + account + ", password=" + password + ", port="
				+ port + ", ip=" + ip + ", creator=" + creator + ", createdate=" + createdate + ", updator=" + updator
				+ ", updatedate=" + updatedate + "]";
	}
}