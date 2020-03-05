package com.maxim;

import java.util.Timer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maxim.contollers.CompaniesController;
import com.maxim.contollers.CustomersController;
import com.maxim.contollers.UsersController;
import com.maxim.entities.Company;
import com.maxim.entities.Customer;
import com.maxim.entities.User;
import com.maxim.enums.ErrorTypes;
import com.maxim.enums.UserType;
import com.maxim.exception.ApplicationException;
import com.maxim.utils.MyTimerTask;

@Component
public class Initializer {
	@Autowired
	UsersController usersController;
	@Autowired
	CustomersController customersController;
	@Autowired
	CompaniesController companiesController;
	@Autowired
	Tester tester;
	@Autowired
	MyTimerTask timerTask;

	
	@PostConstruct
	public void init() throws ApplicationException{
	
	
		//adding a default admin to database
		User user = new User("Max@max.com", "1234".hashCode(), UserType.ADMIN, null);
		String name = "Test Customer1";
		String phone = "000-000";
		int amountOfKids = 0;
		String address = "Test Street";
		boolean isMarried = false;
		User userCustomer = new User("Customer@max.com", "12345".hashCode(), UserType.CUSTOMER, null);
		Customer customer = new Customer(name, phone, amountOfKids, address, isMarried, userCustomer);
		Company company = new Company("maxcom", "maxaddress", "maxphone");
		User userCompany = new User("Company@max.com", "12345".hashCode(), UserType.COMPANY, company );
		try {
			companiesController.createCompany(company);
			usersController.createUser(userCompany);
			usersController.createUser(user);
			customersController.createCustomer(customer);
		} catch (ApplicationException e) {
			if (e.getErrorType()!=ErrorTypes.NAME_ALREADY_EXIST_ERROR) {
				throw e;
			}
		}
		
		//tester.doBasicTests();
		//tester.doAdvancedTests();
		//adding a mock data
		tester.fillDataBaseWithMockData();
		// Tell the timer to run the task every 20 seconds, starting of 30 sec
		//timer works, but disabled 
		//new Timer().scheduleAtFixedRate(timerTask, 30000, 20000);
		}


	
	}

