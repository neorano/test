package com.maxim.serverinternal;

import com.maxim.entities.Company;
import com.maxim.enums.UserType;

public class UserData {
	private long id;
	private UserType userType;
	private Company company;

	public UserData(long id, UserType userType, Company company) {
		this.id = id;
		this.userType = userType;
		this.company = company;
	}

	public long getId() {
		return id;
	}

	public UserType getUserType() {
		return userType;
	}

	public Company getCompany() {
		return company;
	}



}
