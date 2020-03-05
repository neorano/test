package com.maxim.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maxim.contollers.CompaniesController;
import com.maxim.entities.Company;
import com.maxim.entities.Coupon;
import com.maxim.enums.Category;
import com.maxim.exception.ApplicationException;
import com.maxim.searchobjects.CouponSearchObject;
import com.maxim.serverinternal.UserData;
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/companies")


public class CompaniesApi {
	//TODO add options for admin and user of company(so, no need company id)
	@Autowired
	CompaniesController companiesController;
	
	
	@PostMapping
	public void createCompany(@RequestBody Company company) throws ApplicationException {
		
		companiesController.createCompany(company);

	}

	@PutMapping
	public void updateCompany(@RequestBody Company company) throws ApplicationException {
		companiesController.updateCompany(company);
	}

	@GetMapping("{companyId}")
	public Company getCompany(@PathVariable("companyId") long id) throws ApplicationException {
		return companiesController.getCompany(id);
	}

	@DeleteMapping("{companyId}")
	public void deleteCompany(@PathVariable("companyId") long id) throws ApplicationException {
		companiesController.deleteCompany(id);
	}

	@GetMapping()
	public List<Company> getAllCompanies () throws ApplicationException {
		
//	checkIfAdminLogged(request,response);
	

		return companiesController.getAllCompanies();
	}
	
	
	@GetMapping("/crit")
	public List<Company> getCriteriaCompanies () throws ApplicationException {
		List<Company> results = new ArrayList<>();
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
	    Transaction tx = null;
	    
	   try { tx = session.beginTransaction();
		Criteria crit = session.createCriteria(Company.class);
		crit.add(Restrictions.eq("address","maxaddress"));
		
		results = crit.list();
		tx.commit();
	 } catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }
		return results;
	}
	

	@GetMapping("/getCoupons")
	public List<Coupon> getCompanyCoupons(ServletRequest request) throws ApplicationException {
		UserData data = (UserData) request.getAttribute("userData");
		Company company = data.getCompany();
		long id = company.getId();
		
		return companiesController.getCoupons(id);
	}
	@GetMapping("/getCouponsByMaxPrice")
	public List<Coupon> getCompanyCoupons(@RequestParam ("maxPrice") float price, ServletRequest request) 
			throws ApplicationException {
		UserData data = (UserData) request.getAttribute("userData");
		Company company = data.getCompany();
		long id = company.getId();
		return companiesController.getCouponsByMaxPrice(price, id);
//		return companiesController.getCoupons(
//				CouponSearchObject.Builder().setCompanyId(id).
//				setMaxPrice(maxPrice).
//				build()
//				);
	}
	
	@GetMapping("/getCouponsByCategory")
	public List<Coupon> getCompanyCoupons(
			@RequestParam ("category") Category category, ServletRequest request) throws ApplicationException {
		UserData data = (UserData) request.getAttribute("userData");
		Company company = data.getCompany();
		long id = company.getId();
	return companiesController.getCouponsByCategory(category, id);
//		return companiesController.getCoupons(
//				CouponSearchObject.Builder().setCompanyId(id).
//				setCategory(category).
//				build() 
//				);
	}
	
//	private void checkIfAdminLogged(ServletRequest request,ServletResponse response) throws ApplicationException {
//		HttpServletRequest req = (HttpServletRequest) request;
//		UserData userData = (UserData) req.getAttribute("userData");
//
//		if (userData.getUserType()!=UserType.ADMIN) {
//			HttpServletResponse res = (HttpServletResponse) response;
//			//access denied
//			res.setStatus(403);
//			throw new ApplicationException(ErrorTypes.ACCESS_DENIED, "Access denied!");
//		}
//		
//	}
}
