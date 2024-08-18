package com.openclassrooms.medilabo_report.model;

import java.util.List;

public class ReportData {

	private Long id;
	private int age;
	private String sex;
	private List<String> notes;
	private String result = "NOT_DEFINED";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int i) {
		this.age = i;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public List<String> getNotes() {
		return notes;
	}

	public void setNotes(List<String> notes) {
		this.notes = notes;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public ReportData(Long id, int age, String sex, List<String> notes, String result) {
		super();
		this.id = id;
		this.age = age;
		this.sex = sex;
		this.notes = notes;
		this.result = result;
	}

	public ReportData() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ReportData [id=" + id + ", age=" + age + ", sex=" + sex + ", notes=" + notes + ", result=" + result
				+ "]";
	}

}
