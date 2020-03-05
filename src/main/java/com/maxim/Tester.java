package com.maxim;


import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maxim.beans.UserLoginDetails;
import com.maxim.contollers.CompaniesController;
import com.maxim.contollers.CouponsController;
import com.maxim.contollers.CustomersController;
import com.maxim.contollers.PurchasesController;
import com.maxim.contollers.UsersController;
import com.maxim.entities.Company;
import com.maxim.entities.Coupon;
import com.maxim.entities.Customer;
import com.maxim.entities.Purchase;
import com.maxim.entities.User;
import com.maxim.enums.Category;
import com.maxim.enums.ErrorTypes;
import com.maxim.enums.UserType;
import com.maxim.exception.ApplicationException;
import com.maxim.searchobjects.CouponSearchObject;
import com.maxim.searchobjects.PurchaseSearchObject;
import com.maxim.utils.Generator;

@Component
public class Tester {

	@Autowired
	UsersController usersController;
	@Autowired
	CustomersController customersController;
	@Autowired
	CompaniesController companiesController;
	@Autowired
	CouponsController couponsController;
	@Autowired
	PurchasesController purchasesController;
	// public static final boolean PRINT_INFO = true;
	public static boolean DEBUG_MODE = false;

	public void doBasicTests() throws ApplicationException {

		User user = new User("Maxim@qwe.com", new String("Qwer1234").hashCode(), UserType.ADMIN, null);
		User user2 = new User("Noname@qwe.com", new String("1234").hashCode(), UserType.CUSTOMER, null);
		userTests(user, usersController);

		Customer customer = new Customer("Test User", "32-26-33", 2, "Nowhere", false, user2);
		customerTests(customer, customersController);

		Company company = new Company("Aperture Science", "CLASSIFIED", "123-12");
		companyTests(company);

		Coupon coupon = new Coupon("Test coupon", 130.3f, company, Category.GENERAL, 5,
				Date.valueOf("2019-10-9"), Date.valueOf("2029-10-20"), "no description");
		couponTests(coupon);

		Purchase purchase = new Purchase(coupon, customer, 2); 
		purchaseTests(purchase);
		// customersController.deleteCustomer(customer.getId());

		System.out.println("All coupons purchased by customer");
		System.out.println(couponsController
				.getCouponsByCustomer(CouponSearchObject.BuilderWithCustomerId(customer.getId()).build()));

		System.out.println("All basic tests completed");

//		System.out.println("All coupons purchased by customer " + customer);
//		System.out.println(couponsController.getCouponsByCustomer(customer.getId(), null, null));

	}

	public void doAdvancedTests() throws ApplicationException {
		String name = "Test Customer1";
		String phone = "000-000";
		int amountOfKids = 0;
		String address = "Test Street";
		boolean isMarried = false;
		User user = new User("TestCustomerUser1@test.test", 12345, UserType.CUSTOMER, null);
		Customer customer = new Customer(name, phone, amountOfKids, address, isMarried, user);
		// if customer created, corresponding User is created
		System.err.println("Adding customer to database...");

		long id = customersController.createCustomer(customer);
		if (usersController.getUser(id) == null) {
			//if user is not created
			System.err.println("!!!failed to auto create user after customer creation");
			// i'll create user for further tests
			user.setId(id);
			usersController.createUser(user);
		}
		else {
			System.err.println("--corresponding user succesfully created");
		}
		// if customer is deleted -> corresponding user MUST be deleted automatically
		System.err.println("Delete customer...");
		customersController.deleteCustomer(id);
		if (usersController.getUser(id) != null) {
			//if user is not deleted
			System.err.println("!!!failed to auto delete user after customer deletion");
			// i'll delete user for further tests
		usersController.deleteUser(id);
		}
		else {
			System.err.println("--corresponding user succesfully deleted");
		}
		
		// one more customer creation
		System.err.println("Create customer 2...");
		user = new User("TestCustomerUser2@test.test", 12345, UserType.CUSTOMER, null);
		customer = new Customer(name+"222", phone, amountOfKids, address, isMarried, user);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! "+customer);
		id = customersController.createCustomer(customer);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! "+customer);
		if (usersController.getUser(id) == null) {
			//if user is not created
			System.err.println("!!!failed to auto create user after customer creation");
			// i'll create user for further tests
			user.setId(id);
			usersController.createUser(user);
		}
		else {
			System.out.println("--corresponding user succesfully created");
		}
		
		// if User deleted -> corresponding customer MUST be deleted
		System.out.println("Delete User...");
		usersController.deleteUser(id);
		if (customersController.getCustomer(id) != null) {
			//if user is customer not deleted
			System.err.println("!!!failed to auto delete customer after user deletion");
			// i'll delete customer for further tests
		customersController.deleteCustomer(id);
		}
		else {
			System.out.println("--corresponding customer succesfully deleted");
		}
		// if Company is deleted all her Users and Coupons MUST be deleted

	}

	private void purchaseTests(Purchase purchase) throws ApplicationException {
		System.err.println(purchase);
		System.err.println(purchase.getCoupon());
		System.err.println(purchase.getCustomer());
		purchasesController.makePurchase(purchase);
		System.out.println(purchase + " made");

		System.out.println("All purchases by customer:\n"
				// + purchasesController.getPurchases(null, null, purchase.getCustomerId(),
				// null, null, null));
				+ purchasesController.getPurchases(purchase.getCustomer()));
		// how to pass date to database in SQL syntax
		purchasesController.deletePurchase(purchase.getId());
		System.out.println(purchase + " deleted");
		purchasesController.makePurchase(purchase);
		System.out.println(purchase + " added");

	}

	private void couponTests(Coupon coupon) throws ApplicationException {
		couponsController.addCoupon(coupon);
		System.out.println(coupon + " added");
		couponsController.updateCoupon(coupon);
		System.out.println(coupon + " updated");
		
//null, coupon.getCompanyId(), null, null
		couponsController.deleteCoupon(coupon.getId());
		System.out.println(coupon + " deleted");
		couponsController.addCoupon(coupon);
		System.out.println(coupon + " added");

	}

	private void companyTests(Company company) throws ApplicationException {
		companiesController.createCompany(company);
		System.out.println(company + " created");
		companiesController.updateCompany(company);
		System.out.println(company + " updated");
		System.out.println("All companies:\n" + companiesController.getAllCompanies());
		System.out.println("get one company " + companiesController.getCompany(company.getId()));
		companiesController.deleteCompany(company.getId());
		System.out.println(company + " deleted");
		companiesController.createCompany(company);
		System.out.println(company + " created");

	}

	public void fillDataBaseWithMockData() throws ApplicationException {
		// clearDB();

		Generator generator = new Generator();

		int companiesCount = 20;
		int usersCount = 20;
		int couponsCount = 60;
		int purchasesCount = 60;

		int counter = 0;

		for (int i = 0; i < companiesCount; i++) {
			try {

				companiesController.createCompany(generator.generateRandomCompany());
				counter++;
			} catch (ApplicationException e) {
				// we`ll just skip all "name already exist" errors
				if (e.getErrorType() != ErrorTypes.NAME_ALREADY_EXIST_ERROR) {
					e.printStackTrace();
				}
			}

		}
		System.out.println(counter + " companies generated");

		List<Company> listOfCompanies = companiesController.getAllCompanies();

		counter = 0;
		for (int i = 0; i < couponsCount; i++) {

			try {
				couponsController.addCoupon(generator.generateRandomCoupon(listOfCompanies));
				counter++;
			} catch (ApplicationException e) {
				// I'll skip coupons with wrong date
				if (e.getErrorType() != ErrorTypes.COUPON_DATES_NOT_VALID) {
					e.printStackTrace();
				}
			}

		}
		System.out.println(counter + " coupons generated");
		counter = 0;
		for (int i = 0; i < usersCount; i++) {
			User randomUser = generator.generateRandomUser(listOfCompanies);

			try {
				if (randomUser.getUserType() == UserType.CUSTOMER) {
					// if user is customer we must add customer details(with auto adding user)
					customersController.createCustomer(generator.generateRandomCustomer(randomUser));
					counter++;
				} else {
					// else just create user (if user is company there is already company id)
					usersController.createUser(randomUser);
					counter++;
				}
			} catch (ApplicationException e) {
				// we`ll just skip all "name already exist" errors
				if (e.getErrorType() != ErrorTypes.NAME_ALREADY_EXIST_ERROR) {
					e.printStackTrace();

				}
			}

		}
		System.out.println(counter + " users generated");
		counter = 0;
		List<Coupon> listOfCoupons = couponsController.getAllCoupons();
		List<Customer> listOfCustomers = customersController.getAllCustomers();

		for (int i = 0; i < purchasesCount; i++) {
			Purchase randomPurchase = generator.generateRandomPurchase(listOfCoupons, listOfCustomers);
			try {
				purchasesController.makePurchase(randomPurchase);
				counter++;
			} catch (ApplicationException e) {
				// just skip if not enough coupons available or coupon expired or coupon not
				// started
				if (e.getErrorType() != ErrorTypes.NOT_ENOUGH_COUPONS_AVAILABLE
						&& e.getErrorType() != ErrorTypes.COUPON_EXPIRED
						&& e.getErrorType() != ErrorTypes.COUPON_NOT_STARTED) {
					e.printStackTrace();
				}
			}
		}
		System.out.println(counter + " purchases generated");
		counter = 0;

		System.out.println("All random data added to database successfully");

	}

	private static void customerTests(Customer customer, CustomersController customersController)
			throws ApplicationException {
		customersController.createCustomer(customer);
		System.out.println(customer + " created");
		customersController.updateCustomer(customer);
		System.out.println(customer + " updated");
		System.out.println("All customers:\n" + customersController.getAllCustomers());
		System.out.println("get one customer " + customersController.getCustomer(customer.getId()));
		customersController.deleteCustomer(customer.getId());
		System.out.println(customer + " deleted");
		customersController.createCustomer(customer);
		System.out.println(customer + " created");

	}

	private static void userTests(User user, UsersController usersController) throws ApplicationException {
		usersController.createUser(user);
		System.out.println(user + " created");
		usersController.login(new UserLoginDetails("Maxim@qwe.com", new String("Qwer1234")));
		System.out.println("Logged in succesfully");

		System.out.println("all users: \n" + usersController.getAllUsers());
		System.out.println("get one " + usersController.getUser(user.getId()));
		usersController.updateUser(user);
		System.out.println("user updated");
		usersController.deleteUser(user.getId());
		System.out.println(user + " deleted");
		usersController.createUser(user);
		System.out.println(user + " created");

	}

	private void userPasswordValidation(String userName, String password) throws ApplicationException {

		if (password.length() > 15 || password.length() < 6) {
			throw new ApplicationException(ErrorTypes.PASSWORD_ERROR,
					"Password should be less than 15 and more than 6 characters in length.");

		}
		if (password.indexOf(userName) > -1) {
			throw new ApplicationException(ErrorTypes.PASSWORD_ERROR, "Password Should not be same as user name");

		}

		String lowerCaseChars = "(.*[a-z].*)";
		if (!password.matches(lowerCaseChars)) {
			throw new ApplicationException(ErrorTypes.PASSWORD_ERROR,
					"Password should contain atleast one lower case alphabet");

		}
		String numbers = "(.*[0-9].*)";
		if (!password.matches(numbers)) {
			throw new ApplicationException(ErrorTypes.PASSWORD_ERROR, "Password should contain atleast one number.");

		}

	}

}
