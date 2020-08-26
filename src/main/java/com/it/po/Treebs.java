package com.it.po;

import java.util.List;

/**
 * 
 * @ClassName: Treebs  
 * @Description: TODO  菜单树 bootstarp tree
 * @author Administrator  
 * @date 2019年7月29日  
 *
 */
public class Treebs {
	private Integer id;
	private String text;
	private List<Treebs> nodes;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<Treebs> getNodes() {
		return nodes;
	}
	public void setNodes(List<Treebs> nodes) {
		this.nodes = nodes;
	}
	
}
