package com.maxim.interfaces;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.maxim.entities.Customer;
import com.maxim.entities.Purchase;

public interface IPurchasesDAO extends CrudRepository<Purchase, Long> {
	
	List<Purchase> findById(long purchaseId);
	List<Purchase> findByCoupon(long purchaseId);
	List<Purchase> findByCustomer(Customer customer);
	List<Purchase> findByAmount(int amount);
//	List<Purchase> findByStartDateAfter(Timestamp fromDate);
//	List<Purchase> findByStartDateBefore(Timestamp toDate);
}

//private Long purchaseId;
//private Long couponId;
//private Long customerId;
//private Integer amount;
//private Timestamp fromDate;
//private Timestamp toDate;