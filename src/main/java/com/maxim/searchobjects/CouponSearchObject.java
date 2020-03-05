package com.maxim.searchobjects;

import java.sql.Date;

import com.maxim.enums.Category;

public class CouponSearchObject {
	private Long couponId;
	private Long customerId;
	private Float maxPrice;
	private Long companyId;
	private Category category;
	private Integer amount;
	private Date startDate;
	private Date endDate;

	public CouponSearchObject(long customerId) {
		this.customerId = customerId;
	}
	

	public CouponSearchObject() {
	}

	public Long getCouponId() {
		return couponId;
	}


	public Long getCustomerId() {
		return customerId;
	}


	public Float getMaxPrice() {
		return maxPrice;
	}


	public Long getCompanyId() {
		return companyId;
	}


	public Category getCategory() {
		return category;
	}


	public Integer getAmount() {
		return amount;
	}


	public Date getStartDate() {
		return startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public static Builder BuilderWithCustomerId(long customerId) {
		//this is needed for function in DAO with JOIN
		return new CouponSearchObject(customerId).new Builder();
	}
	public static Builder Builder() {
		//this is needed for function in DAO with JOIN
		return new CouponSearchObject().new Builder();
	}

	public class Builder {

		private Builder() {
			// private constructor
		}
		public Builder setCouponId(long couponId) {
			CouponSearchObject.this.couponId = couponId;
			return this;
		}
		public Builder setMaxPrice(float maxPrice) {
			CouponSearchObject.this.maxPrice = maxPrice;
			return this;
		}
		public Builder setCompanyId(long companyId) {
			CouponSearchObject.this.companyId = companyId;
			return this;
		}
		public Builder setCategory(Category category) {
			CouponSearchObject.this.category = category;
			return this;
		}
		public Builder setAmount(int amount) {
			CouponSearchObject.this.amount = amount;
			return this;
		}
		public Builder setStartDate(Date startDate) {
			CouponSearchObject.this.startDate = startDate;
			return this;
		}
		public Builder setEndDate(Date endDate) {
			CouponSearchObject.this.endDate = endDate;
			return this;
		}
		public CouponSearchObject build() {
            return CouponSearchObject.this;
        }
		
	}
}
