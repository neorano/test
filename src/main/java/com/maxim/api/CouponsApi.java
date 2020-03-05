package com.maxim.api;

import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxim.contollers.CouponsController;
import com.maxim.entities.Company;
import com.maxim.entities.Coupon;
import com.maxim.exception.ApplicationException;
import com.maxim.serverinternal.UserData;
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/coupons")
public class CouponsApi {
	
	@Autowired
	CouponsController couponsController;
	@PostMapping
	//TODO crud coupon for specific company(by login data)
	public void createCoupon(@RequestBody Coupon coupon, ServletRequest request) throws ApplicationException {
		UserData data = (UserData) request.getAttribute("userData");
		Company company = data.getCompany();
		coupon.setCompany(company);
		couponsController.addCoupon(coupon);

	}

	@PutMapping
	public void updateCoupon(@RequestBody Coupon coupon, ServletRequest request) throws ApplicationException {
		UserData data = (UserData) request.getAttribute("userData");
		Company company = data.getCompany();
		coupon.setCompany(company);
		couponsController.updateCoupon(coupon);
	}

	@GetMapping("{couponId}")
	public Coupon getCoupon(@PathVariable("couponId") long id) throws ApplicationException {
		return couponsController.getCoupon(id);
	}

	@DeleteMapping("{couponId}")
	public void deleteCoupon(@PathVariable("couponId") long id) throws ApplicationException {
		couponsController.deleteCoupon(id);
	}

	@GetMapping()
	public List<Coupon> getAllCoupons () throws ApplicationException {
		return couponsController.getAllCoupons();
	}
	
	//TODO get all coupons of company
}
