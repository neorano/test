package com.maxim.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Customer {
	@Id
	@GeneratedValue
	private long id;
	@Column( nullable = false)
	private String name;
	@Column( nullable = false)
	private String phone;
	@Column( nullable = false)
	private int amountOfKids;
	@Column( nullable = false)
	private String address;
	@Column( nullable = false)
	private boolean isMarried;
	@JsonIgnore
	@OneToMany (mappedBy = "customer", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private List <Purchase> purchases;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@MapsId 
	private User user;

	public Customer() {
		super();
	}

	public Customer(long id,String name, String phone, int amountOfKids, String address, boolean isMarried, User user) {
		this(name, phone, amountOfKids, address, isMarried, user);
		this.id = id;

	}

	public Customer(String name, String phone, int amountOfKids, String address, boolean isMarried, User user) {
		super();
		this.name = name;
		this.phone = phone;
		this.amountOfKids = amountOfKids;
		this.address = address;
		this.isMarried = isMarried;
		this.user = user;
	}

	public List<Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}

	public String getPhone() {
		return phone;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getAmountOfKids() {
		return amountOfKids;
	}

	public void setAmountOfKids(int amountOfKids) {
		this.amountOfKids = amountOfKids;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean getIsMarried() {
		return isMarried;
	}

	public void setIsMarried(boolean isMarried) {
		this.isMarried = isMarried;
	}

	public long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", phone=" + phone + ", amountOfKids=" + amountOfKids
				+ ", address=" + address + ", isMarried=" + isMarried + ", user=" + user.getUsername() + "]";
	}



}
