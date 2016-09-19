package com.standardproject.bean;
/**
 * 分类实体类,装载下拉框的数据
 * @author cgd
 * 2016-2-25
 */
public class RootCategory {
	private int id;
	private String name;
	public RootCategory() {
		super();
	}
	public RootCategory(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
