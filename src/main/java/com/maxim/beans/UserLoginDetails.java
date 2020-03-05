package com.maxim.beans;

public class UserLoginDetails {
	// object send by client for login to server
	private String username;
	private String password;

	public UserLoginDetails(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public UserLoginDetails() {
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
