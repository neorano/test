package com.maxim.contollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.maxim.entities.Customer;
import com.maxim.enums.ErrorTypes;
import com.maxim.exception.ApplicationException;
import com.maxim.interfaces.ICustomersDAO;

@Controller
public class CustomersController {
	@Autowired
	private ICustomersDAO customersDAO;
	@Autowired
	private UsersController usersController;

	public long createCustomer(Customer customer) throws ApplicationException {

		customerValidations(customer);
		try {
			long id = customersDAO.save(customer).getId();
			customer.setId(id);
			return id;
		} catch (Exception e) {
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}
	}

	private void customerValidations(Customer customer) throws ApplicationException {
		
//		if (customer.getAddress() == null) {
//			throw new ApplicationException(ErrorTypes.INCORRECT_CUSTOMER, "Address cannot be null");
//
//		}
//		if (customer.getPhone() == null) {
//			throw new ApplicationException(ErrorTypes.INCORRECT_CUSTOMER, "Phone cannot be null");
//		}
//
//		if (customer.getName() == null) {
//			throw new ApplicationException(ErrorTypes.INCORRECT_CUSTOMER, "Name cannot be null");
//		}
		if (customer.getUser() == null) {
			throw new ApplicationException(ErrorTypes.INCORRECT_CUSTOMER, "User cannot be null");
		}
		usersController.updateUserValidations(customer.getUser());
	}

	public void updateCustomer(Customer customer) throws ApplicationException {
		customerValidations(customer);
		try {
			customersDAO.save(customer);
		} catch (Exception e) {
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}
	}

	public void deleteCustomer(long customerId) throws ApplicationException {
		
		try {
			customersDAO.delete(customerId);
		} catch (Exception e) {
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}
	}

	public List<Customer> getAllCustomers() throws ApplicationException {
		try {
			return (List<Customer>) customersDAO.findAll();
		} catch (Exception e) {
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}
	}

	public Customer getCustomer(long customerID) throws ApplicationException {

		try {
			return customersDAO.findOne(customerID);
		} catch (Exception e) {
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}
	}

}
