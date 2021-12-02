package com.qifeng.will.basicknowledgecode.commomentity;

public class Student {
	
	private String id;
	private String name;
	private String sex;
	private String phone;
	private String address;
	
	public Student(String id, String name, String sex,String phone,String address) {
		this.id=id;
		this.name=name;
		this.sex=sex;
		this.phone=phone;
		this.address=address;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", sex=" + sex
				+ ", phone=" + phone + ", address=" + address + "]";
	}
	
	

}
