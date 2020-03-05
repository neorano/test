package com.maxim.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.maxim.enums.UserType;
@Entity
public class User {
	@Id
	@GeneratedValue
	private long id;
	@Column( unique = true, nullable = false)
	private String username;
	@Column(nullable = false)
	private int passwordHash;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private UserType userType;
	
	@ManyToOne( fetch=FetchType.EAGER)
	private Company company;
	


	public User() {
		super();
	}

	public User(String username, int passwordHash, UserType userType, Company company) {
		super();
		this.username = username;
		this.passwordHash = passwordHash;
		this.userType = userType;
		this.company = company;
	}

	public User(long id, String username, int passwordHash, UserType userType, Company company) {
		this(username, passwordHash, userType, company);
		this.id = id;

	}

	


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getPasswordHash() {
		return passwordHash;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setPasswordHash(int passwordHash) {
		this.passwordHash = passwordHash;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}


	public long getId() {
		return id;
	}

	public UserType getUserType() {
		return userType;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", passwordHash=" + passwordHash + ", userType=" + userType
				+ ", company=" + company==null?company.getName():"" + "]";
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
