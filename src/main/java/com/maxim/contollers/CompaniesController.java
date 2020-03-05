package com.maxim.contollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.maxim.entities.Company;
import com.maxim.entities.Coupon;
import com.maxim.enums.Category;
import com.maxim.enums.ErrorTypes;
import com.maxim.exception.ApplicationException;
import com.maxim.interfaces.ICompaniesDAO;
import com.maxim.searchobjects.CouponSearchObject;

@Controller
public class CompaniesController {
	@Autowired
	private ICompaniesDAO companiesDAO;
	@Autowired
	private CouponsController couponsController;

	// create
	public long createCompany(Company company) throws ApplicationException {

		createCompanyValidations(company);
		companyValidations(company);

		try {
			Company savedCompany = companiesDAO.save(company);
			long id = savedCompany.getId();
			company.setId(id);
			return id;
		} catch (Exception e) {
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}
	}

	private void createCompanyValidations(Company company) throws ApplicationException {
		try {
			if (companiesDAO.findByName(company.getName()) != null) {

				throw new ApplicationException(ErrorTypes.NAME_ALREADY_EXIST_ERROR, "Company name already exist");
			}
		} catch (Exception e) {
			if (e instanceof ApplicationException) {
				throw e;
			}
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}

	}

	// update
	public void updateCompany(Company company) throws ApplicationException {
		companyValidations(company);
		try {
			companiesDAO.save(company);
		} catch (Exception e) {
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}

		return;
	}

	private void companyValidations(Company company) throws ApplicationException {
		if (company.getAddress() == null) {
			throw new ApplicationException(ErrorTypes.INCORRECT_COMPANY, "Company address cannot be null");
		}
		if (company.getName() == null) {
			throw new ApplicationException(ErrorTypes.INCORRECT_COMPANY, "Company name cannot be null");
		}
		if (company.getPhone() == null) {
			throw new ApplicationException(ErrorTypes.INCORRECT_COMPANY, "Company phone cannot be null");
		}

	}

	// delete
	public void deleteCompany(long companyId) throws ApplicationException {

		try {
			companiesDAO.delete(companyId);
		} catch (Exception e) {
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}

		return;
	}

	// read all
	public List<Company> getAllCompanies() throws ApplicationException {
		List<Company> list;
		try {
			list = (List<Company>) companiesDAO.findAll();
			return list;
		} catch (Exception e) {
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}

	}

	// read
	public Company getCompany(long companyID) throws ApplicationException {
		Company company;
		try {
			company = companiesDAO.findOne(companyID);
			return company;
		} catch (Exception e) {
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}

	}
	public List<Coupon> getCouponsByCategory(Category category, long id) throws ApplicationException {

		return couponsController.getCouponsByCategory(category,id);
	}

	public List<Coupon> getCouponsByMaxPrice(float price,long id) throws ApplicationException {

		return couponsController.getCouponsByMaxPrice(price,id);
	}
	

	public List<Coupon> getCoupons(long companyId) throws ApplicationException {
		List<Coupon> list = couponsController.getCouponsByCompanyId(companyId);
		
		return list;
	}

}
