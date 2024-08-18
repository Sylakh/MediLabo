package com.openclassrooms.medilabo_frontend.beans;

public class ReportDataDTOBeans {

	private Long id;
	private String result;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public ReportDataDTOBeans(Long id, String result) {
		super();
		this.id = id;
		this.result = result;
	}

	public ReportDataDTOBeans() {
		super();
		// TODO Auto-generated constructor stub
	}

}
