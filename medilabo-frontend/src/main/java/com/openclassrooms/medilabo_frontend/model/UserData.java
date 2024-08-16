package com.openclassrooms.medilabo_frontend.model;

public class UserData {
	private String username;
	private String password;

	// Getters et Setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserData(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public UserData() {
		super();
	}

}
