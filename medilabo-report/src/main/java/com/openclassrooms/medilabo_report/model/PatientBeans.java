package com.openclassrooms.medilabo_report.model;

import java.sql.Date;

public class PatientBeans {

	private int id;
	private String lastName;
	private String firstName;
	private Date birthday;
	private String gender;
	private String address;
	private String phone;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public PatientBeans(int id, String lastName, String firstName, Date birthday, String gender, String address,
			String phone) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.birthday = birthday;
		this.gender = gender;
		this.address = address;
		this.phone = phone;
	}

	public PatientBeans() {
		super();
	}

	@Override
	public String toString() {
		return "PatientBeans [id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", birthday="
				+ birthday + ", gender=" + gender + ", address=" + address + ", phone=" + phone + "]";
	}

}
