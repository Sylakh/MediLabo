package com.openclassrooms.microback.modele;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

@Entity
@Data
@Table(name = "patient")
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "lastname")
	@NotBlank(message = "lastname is mandatory")
	private String lastName;

	@Column(name = "firstname")
	@NotBlank(message = "firstname is mandatory")
	private String firstName;

	@Column(name = "birthday")
	@NotNull(message = "birthday is mandatory")
	@Past
	private Date birthday;

	@Column(name = "gender")
	@NotBlank(message = "gender is mandatory")
	private String gender;

	@Column(name = "address")
	private String address;

	@Column(name = "phone")
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

	public Patient(int id, String lastName, String firstName, Date birthday, String gender, String address,
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

	public Patient() {
		super();
	}

}
