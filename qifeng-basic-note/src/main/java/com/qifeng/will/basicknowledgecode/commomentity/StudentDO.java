package com.qifeng.will.basicknowledgecode.commomentity;

public class StudentDO {


	private String id;
	private String name;
	private String sex;

	public StudentDO(){

    }
	
	public StudentDO(String id,String name,String sex){
		this.id=id;
		this.name=name;
		this.sex=sex;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "StudentDO [id=" + id + ", name=" + name + ", sex=" + sex + "]";
	}
	

}
