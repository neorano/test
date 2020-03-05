package com.maxim.interfaces;




import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.maxim.entities.Coupon;
import com.maxim.enums.Category;

public interface ICouponsDAO extends CrudRepository<Coupon, Long> {

	void deleteByEndDateLessThan (Date todayDate);

	void deleteByCompanyId(long companyId);
	
	List<Coupon> findByCompanyId ( long companyId);
	
	List<Coupon> findByCategoryAndCompanyId (Category category , long companyId);
	
	List<Coupon> findByPriceLessThanAndCompanyId (float price,long companyId);
}