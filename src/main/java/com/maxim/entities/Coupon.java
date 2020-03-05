package com.maxim.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maxim.enums.Category;
@Entity
public class Coupon {
	@Id
	@GeneratedValue
	private long id;
	@Column( nullable = false)
	private String title;
	@Column(  nullable = false)
	private float price;
	@ManyToOne
	private Company company;
	@OneToMany (mappedBy = "coupon", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JsonIgnore
	private List <Purchase> purchases;
	@Column( nullable = false)
	@Enumerated(EnumType.STRING)
	private Category category;
	@Column( nullable = false)
	private int amount;
	@Column( nullable = false)
	private Date startDate;
	@Column( nullable = false)
	private Date endDate;
	@Column
	private String description;

	public Coupon() {
		super();
	}

	public Coupon(long id, String title, float price, Company company, Category category, int amount, Date startDate,
			Date endDate,String description ) {
		this(title, price, company, category, amount, startDate, endDate, description);
		this.id = id;

	}

	public Coupon(String title, float price, Company company, Category category, int amount, Date startDate,
			Date endDate,String description) {
		super();
		this.title = title;
		this.price = price;
		this.company = company;
		this.category = category;
		this.amount = amount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
	}

	public List<Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}

	public int getAmount() {
		return amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}


	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public long getId() {
		return id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", price=" + price + ", company=" + company.getName() + ", category="
				+ category + ", amount=" + amount + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", description=" + description + "]";
	}

	

}
