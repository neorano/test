package com.maxim.contollers;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.maxim.entities.Coupon;
import com.maxim.entities.Customer;
import com.maxim.entities.Purchase;
import com.maxim.enums.ErrorTypes;
import com.maxim.exception.ApplicationException;
import com.maxim.interfaces.IPurchasesDAO;
import com.maxim.searchobjects.PurchaseSearchObject;
import com.maxim.serverinternal.UserData;
@Controller
public class PurchasesController {
	@Autowired
	private IPurchasesDAO purchasesDAO;
	@Autowired
	private CouponsController couponsController;
	

	public long makePurchase (Purchase purchase) throws ApplicationException {

	
	purchaseValidations(purchase);
	
	Coupon coupon = couponsController.getCoupon(purchase.getCoupon().getId());

	int purchaseAmount = purchase.getAmount();
	int couponsAmount  = coupon.getAmount();
	Date today = new Date(System.currentTimeMillis());
//	System.out.println(coupon.getStartDate().getTime());
//	System.out.println(today.getTime());
	if (coupon.getStartDate().after(today)) {
		throw new ApplicationException(ErrorTypes.COUPON_NOT_STARTED, 
				"Coupon start date " + coupon.getStartDate() +" today is "+today);
	}
	
	//here i must check date with date of tomorrow date
	// end date is at 00:00 and any date after this moment, but at same date is considered "after"
	//so, I must add to expire date almost 24 hours (23:59:59.9999)
	if (new Date(coupon.getEndDate().getTime()+TimeUnit.DAYS.toMillis(1)-1).before(today)) {
		throw new ApplicationException(ErrorTypes.COUPON_EXPIRED, 
				"Coupon expired at " + coupon.getEndDate() +" today is "+today);
	}
	
	if (purchaseAmount>couponsAmount) {
		throw new ApplicationException(ErrorTypes.NOT_ENOUGH_COUPONS_AVAILABLE, 
				"Available "+couponsAmount + ", but needed " + purchaseAmount);
	}
	
	try {
		Purchase newPurchase =   purchasesDAO.save (purchase);
		long id =newPurchase.getId();
		purchase.setId(id);
		coupon.setAmount(couponsAmount-purchaseAmount);
		//saving data in DB
		couponsController.updateCoupon(coupon);
		return id;
	} catch (Exception e) {
		throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
	}
	}

	private void purchaseValidations(Purchase purchase) {
		// TODO Auto-generated method stub
		
	}

	public void deletePurchase(long purchaseId) throws ApplicationException {
		try {
			purchasesDAO.delete(purchaseId);
		} catch (Exception e) {
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}
	}


	public List<Purchase> getPurchases(Customer customer) throws ApplicationException {
	List<Purchase> list = null;
//	list= findById(long purchaseId);
//	List<Purchase> findByCoupon(long purchaseId);
//	List<Purchase> findByCustomer(long purchaseId);
//	List<Purchase> findByAmount(int amount);
//	List<Purchase> findByStartDateAfter(Timestamp fromDate);
//	List<Purchase> findByStartDateBefore(Timestamp toDate);
	list = purchasesDAO.findByCustomer(customer);
		return list;
		//return purchasesDAO.getPurchases(searchObject);
	}
	
	public Purchase getPurchase(long purchaseId) throws ApplicationException {
		try {
			return purchasesDAO.findOne(purchaseId);
		} catch (Exception e) {
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}
	}
@Deprecated
//updating purchase can be problematic
	public void updatePurchase(Purchase purchase) throws ApplicationException {
		try {
			purchasesDAO.save(purchase);
		} catch (Exception e) {
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}
		
	}



}
