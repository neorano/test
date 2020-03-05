package com.maxim.contollers;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.maxim.entities.Company;
import com.maxim.entities.Coupon;
import com.maxim.enums.Category;
import com.maxim.enums.ErrorTypes;
import com.maxim.exception.ApplicationException;
import com.maxim.interfaces.ICouponsDAO;
import com.maxim.searchobjects.CouponSearchObject;
import com.maxim.serverinternal.UserData;

@Controller
public class CouponsController {
	@Autowired
	private ICouponsDAO couponsDAO;

	public long addCoupon(Coupon coupon) throws ApplicationException {

		couponValidations(coupon);
		try {
			Coupon savedCoupon = couponsDAO.save(coupon);
			coupon = savedCoupon;
			return coupon.getId();
		} catch (Exception e) {
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}
	}

	private void couponValidations(Coupon coupon) throws ApplicationException {
		if (coupon.getEndDate().before(coupon.getStartDate())) {
			throw new ApplicationException(ErrorTypes.COUPON_DATES_NOT_VALID,
					"Coupon expire date before coupon start date");
		}

	}

	public void deleteCouponsByCompany(long companyId) throws ApplicationException {
		try {
			couponsDAO.deleteByCompanyId(companyId);
		} catch (Exception e) {
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}
	}

	public Coupon getCoupon(long id) throws ApplicationException {
		try {
			return couponsDAO.findOne(id);
		} catch (Exception e) {
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}
	}

	public void updateCoupon(Coupon coupon) throws ApplicationException {
		couponValidations(coupon);
		try {
			couponsDAO.save(coupon);
		} catch (Exception e) {
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}

	}

	public void deleteCoupon(long couponId) throws ApplicationException {
//		
//		PurchasesController purchasesController = new PurchasesController();
//		purchasesController.deletePurchasesByCouponOrCustomer(couponId, null);
		try {
			couponsDAO.delete(couponId);
		} catch (Exception e) {
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}
	}

	public List<Coupon> getCouponsByCategory(Category category, long id) throws ApplicationException {

		return couponsDAO.findByCategoryAndCompanyId(category,id);
	}

	public List<Coupon> getCouponsByMaxPrice(float price,long id) throws ApplicationException {

		return couponsDAO.findByPriceLessThanAndCompanyId(price,id);
	}

//TODO
	public List<Coupon> getCouponsByCustomer(CouponSearchObject couponSearchObject) throws ApplicationException {
		return null;

		// return couponsDAO.getCouponsByCustomer(couponSearchObject);
	}

	public List<Coupon> getCouponsByCompanyId(long companyId) throws ApplicationException {

		return couponsDAO.findByCompanyId(companyId );
	}

	@Transactional
	public void removeOldCoupons(Date todayDate) throws ApplicationException {

		try {
			couponsDAO.deleteByEndDateLessThan(todayDate);
		} catch (Exception e) {
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}

	}

	public List<Coupon> getAllCoupons() throws ApplicationException {
		try {
			return (List<Coupon>) couponsDAO.findAll();
		} catch (Exception e) {
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}

	}

}
