package com.maxim.searchobjects;

import java.sql.Timestamp;

public class PurchaseSearchObject {
	private Long purchaseId;
	private Long couponId;
	private Long customerId;
	private Integer amount;
	private Timestamp fromDate;
	private Timestamp toDate;

	public Long getPurchaseId() {
		return purchaseId;
	}

	public Long getCouponId() {
		return couponId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public Integer getAmount() {
		return amount;
	}

	public Timestamp getFromDate() {
		return fromDate;
	}

	public Timestamp getToDate() {
		return toDate;
	}
	
	   public static Builder Builder() {
	        return new PurchaseSearchObject().new Builder();
	    }

	public class Builder {

        private Builder() {
            // private constructor
        }

        public Builder setPurchaseId(long purchaseId) {
        	PurchaseSearchObject.this.purchaseId = purchaseId;
            return this;
        }
        
        public Builder setCouponId(long couponId) {
        	PurchaseSearchObject.this.couponId = couponId;
            return this;
        }
        
        public Builder setCustomerId(long customerId) {
        	PurchaseSearchObject.this.customerId = customerId;
            return this;
        }

        public Builder setAmount(int amount) {
        	PurchaseSearchObject.this.amount = amount;

            return this;
        }
        public Builder setFromDate(Timestamp fromDate) {
        	PurchaseSearchObject.this.fromDate = fromDate;

            return this;
        }
        public Builder setToDate(Timestamp toDate) {
        	PurchaseSearchObject.this.toDate = toDate;

            return this;
        }

        public PurchaseSearchObject build() {
            return PurchaseSearchObject.this;
        }

    }
	
}
