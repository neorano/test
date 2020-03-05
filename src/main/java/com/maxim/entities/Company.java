package com.maxim.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
public class Company {
	@Id
	@GeneratedValue
	private long id;
	
	@Column( unique = true, nullable = false)
	private String name;
	
	@Column( nullable = false)
	private String address;
	
	@Column( nullable = false)
	private String phone;
	
	@JsonIgnore
	@OneToMany (mappedBy = "company",cascade = CascadeType.REMOVE , fetch = FetchType.LAZY)
	private List<User> employees;
	
	@OneToMany (mappedBy = "company",cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JsonIgnore
	private List <Coupon> coupons;

	public Company() {
		super();
	}

	public Company(String name, String address, String phone) {
		super();
		this.name = name;
		this.address = address;
		this.phone = phone;
	}

	public Company(long id, String name, String address, String phone) {
		this(name, address, phone);
		this.id = id;

	}

	public List<User> getEmployees() {
		return employees;
	}

	public void setEmployees(List<User> employees) {
		this.employees = employees;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	public void setId(long id) {
		this.id = id;
	}

		public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", address=" + address + ", phone=" + phone + "]";
	}
	

}
