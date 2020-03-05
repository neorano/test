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

import com.maxim.contollers.CustomersController;
import com.maxim.contollers.PurchasesController;
import com.maxim.entities.Customer;
import com.maxim.entities.Purchase;
import com.maxim.exception.ApplicationException;
import com.maxim.searchobjects.PurchaseSearchObject;
import com.maxim.serverinternal.UserData;
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/purchases")
public class PurchasesApi {
	@Autowired
	private PurchasesController purchasesController;
	@Autowired
	private CustomersController customersController;
	@PostMapping
	public void createPurchase(@RequestBody Purchase purchase, ServletRequest request) throws ApplicationException {
		UserData data = (UserData) request.getAttribute("userData");
		//setting a customer from cache
		purchase.setCustomer( customersController.getCustomer(data.getId()));
		
		
		purchasesController.makePurchase(purchase);

	}

	@PutMapping
	public void updatePurchase(@RequestBody Purchase purchase) throws ApplicationException {
		//purchasesController.updatePurchase(purchase);
		System.out.println("not accessible update purchase");
	}

	@GetMapping("{purchaseId}")
	public Purchase getPurchase(@PathVariable("purchaseId") long id) throws ApplicationException {
		return purchasesController.getPurchase(id);
	}

	@DeleteMapping("{purchaseId}")
	public void deletePurchase(@PathVariable("purchaseId") long id) throws ApplicationException {
		purchasesController.deletePurchase(id);
	}

	@GetMapping()
	public List<Purchase> getAllCustomerPurchases ( ServletRequest request) throws ApplicationException {
		UserData data = (UserData) request.getAttribute("userData");
		Customer customer = customersController.getCustomer(data.getId());
		return purchasesController.getPurchases(customer);
	}
}
