package com.maxim.api;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

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

import com.maxim.contollers.CustomersController;
import com.maxim.entities.Customer;
import com.maxim.exception.ApplicationException;
import com.maxim.serverinternal.UserData;
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/customers")
public class CustomersApi {
	@Autowired
	CustomersController customersController;
	@PostMapping
	public void createCustomer(@RequestBody Customer customer,ServletRequest request) throws ApplicationException {
		HttpServletRequest req = (HttpServletRequest) request;
		String userPassword = req.getHeader("password");
		
		customer.getUser().setPasswordHash(userPassword.hashCode());
		customersController.createCustomer(customer);

	}

	@PutMapping
	public void updateCustomer(@RequestBody Customer customer,ServletRequest request) throws ApplicationException {
		HttpServletRequest req = (HttpServletRequest) request;
		String userPassword = req.getHeader("password");
		customer .getUser().setPasswordHash(userPassword.hashCode());
		UserData data = (UserData) request.getAttribute("userData");
		// gettin customer id from cache
		customer.setId( data.getId());
		customersController.updateCustomer(customer);
	}

		
		


	@GetMapping("{CustomerId}")
	public Customer getCustomer(@PathVariable("CustomerId") long id) throws ApplicationException {
		return customersController.getCustomer(id);
	}
	@GetMapping
	public Customer getCustomer(ServletRequest request) throws ApplicationException {
		UserData data = (UserData) request.getAttribute("userData");
		
		return customersController.getCustomer(data.getId());
	}

	@DeleteMapping("{CustomerId}")
	public void deleteCustomer(@PathVariable("CustomerId") long id) throws ApplicationException {
		customersController.deleteCustomer(id);
	}

	@GetMapping("/all")
	public List<Customer> getAllCustomers () throws ApplicationException {
		return customersController.getAllCustomers();
	}
}
