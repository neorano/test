package com.maxim.beans;

import com.maxim.entities.Company;
import com.maxim.enums.UserType;

public class UserLoginToken {
	// Object send by server to client on successful login
	private String token;
	private UserType userType;
	private Company company;

	public UserLoginToken(String token, UserType userType,Company company) {
		this.token = token;
		this.userType = userType;
		this.company = company;
	}

	public UserLoginToken() {
	}

	public String getToken() {
		return token;
	}

	public UserType getUserType() {
		return userType;
	}

	

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

}
