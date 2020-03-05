package com.maxim.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
@Entity
public class Purchase {
	@Id
	@GeneratedValue
	private long id;
	@ManyToOne (fetch = FetchType.EAGER)
	private Coupon coupon;
	@ManyToOne (fetch = FetchType.EAGER)
	private Customer customer;
	@Column( nullable = false)
	private int amount;
	//@Column( nullable = false)
	@CreationTimestamp
	private Timestamp timestamp;

	public Purchase() {
		super();
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Purchase(long id, Coupon coupon, Customer customer, int amount, Timestamp timestamp) {
		this(coupon, customer, amount);
		this.id = id;
		this.timestamp = timestamp;

	}

	public Purchase(Coupon coupon, Customer customer, int amount) {
		super();
		this.coupon = coupon;
		this.customer = customer;
		this.amount = amount;
		
	}


	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}


	public int getAmount() {
		return amount;
	}


	@Override
	public String toString() {
		return "Purchase [id=" + id + ", couponId=" + coupon.getTitle() + ", customer=" + customer.getName() + ", amount=" + amount
				+ ", dateTime=" + timestamp + "]";
	}
	
}
