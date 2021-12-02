package com.qifeng.will.basicknowledgecode.commomentity;

public class StudentVO extends StudentDO  implements Comparable<StudentVO>{

    public StudentVO(){

    }
	
	public StudentVO(String id, String name, String sex,Integer age,String phone,String address) {
		super(id, name, sex);
		this.phone=phone;
		this.address=address;
		this.age=age;
	}
	private String phone;
	private String address;
    private Integer age;
	
	
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
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentVO studentVO = (StudentVO) o;

        return age.equals(studentVO.age);
    }

    @Override
    public int hashCode() {
        return age.hashCode();
    }


    @Override
    public String toString() {

        return "StudentVO{" +super.toString()+
                ", age='" + age + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public int compareTo(StudentVO o) {
        return Integer.valueOf(this.age).compareTo(o.age);
    }
}
