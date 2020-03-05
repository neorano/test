package com.maxim.utils;

import java.sql.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.maxim.entities.Company;
import com.maxim.entities.Coupon;
import com.maxim.entities.Customer;
import com.maxim.entities.Purchase;
import com.maxim.entities.User;
import com.maxim.enums.Category;
import com.maxim.enums.UserType;

public class Generator {

	private Random rand = new Random();
	String mockPhones[] = { "+972-585-5546-76", "+972-525-5557-28", "+972-552-3555-82", "+972-505-5565-52",
			"+972-559-5559-49", "+972-558-5557-12", "+972-559-5555-12", "+972-585-5570-22", "+972-585-5596-42",
			"+972-525-5552-31", "+972-535-5581-93", "+972-552-2555-90", "+972-585-5542-40", "+972-558-5559-32",
			"+972-535-5583-66", "+972-552-3555-56", "+972-556-5558-50", "+972-585-5540-95", "+972-505-5539-32",
			"+972-585-5593-03", "+972-525-5535-17", "+972-556-5556-34", "+972-552-3555-24", "+972-556-5557-95",
			"+972-557-1555-91", "+972-505-5560-26", "+972-557-1555-97", "+972-545-5575-07", "+972-557-0555-35",
			"+972-585-5544-41", "+972-525-5561-97", "+972-556-5555-00", "+972-505-5580-14", "+972-525-5599-64",
			"+972-556-5557-98", "+972-535-5553-28", "+972-558-5554-84", "+972-552-3555-24", "+972-557-1555-44",
			"+972-552-2555-40"

	};
	String mockUserNames[] = { "Alvaro", "Tristan", "Gay", "Hilario", "Alane", "Kareem", "Darleen", "Ming", "Tempie",
			"Lue", "Milan", "Thomasina", "Alona", "Elisabeth", "Earl", "Alma", "Latrice", "Laine", "Lance"

	};
	String mockSecondNames[] = {

			"Mcamis", "Chiou", "Schwalb", "Wiegand", "Trapani", "Thorsen", "Ramos", "Coyle", "Terpstra", "Oatman",
			"Rabideau", "Astin", "Boulden", "Hume", "Vasconcellos", "Rady", "Terrazas", "Coutu", "Delvalle", "Brekke",
			"Ratley", "Whittaker", "Babich", "Croom", "Gomez", "Bourland", "Snowden", "Tigner", " Cape"

	};

	String mockEmailAddresses[] = { "@icloud.com", "@live.com", "@msn.com", "@hotmail.com", "@verizon.net", "@yahoo.ca",
			"@aol.com", "@comcast.net", "@mac.com", "@me.com",

	};
	String mockAddresses[] = { "11 Tuval, Ramat Gan", "34 Habarzel, Tel Aviv", "2 Feldman, Petah Tikva",
			"3 Gordon, Jerusalem", "2 Wallenberg Raul, Tel Aviv", "9 Schocken, Tel Aviv", "1 Yegia Kapaim, Tel Aviv",
			"5 Brandeis, Tel Aviv", "9 Nakar Yosef, Petah Tikva", "24 Tchernihovsky, Tel Aviv",
			"1 Hanassi Weizmann, Hadera", "26 Hamerkava, Holon", "5 Homa, Rishon Lezion", "3 hu Yoni, Netanya",
			"4 Hagalil, Jerusalem", "8 Hasadna, Petah Tikva", "1 Bialik, Ramat Gan" };

	String mockCompanyNames[] = { "Jupiter Aviation", "Grasshopper Enterprises", "Cave Security", "Loki Coms",
			"Shade Security", "Tundra Softwares", "Silverecords", "Chiefoods", "Seedtronics", "Tortoisecurity",
			"Thorecords", "Lionetworks", "Ocean force", "Peach hive", "Zeus phone", "Nimble dew", "Global shack",
			"Blue ware", "Rose point", "Leopard cloud"

	};

	public User generateRandomUser(List<Company> listOfCompanies) {
		String username = getRandom(mockUserNames) + getRandom(mockEmailAddresses);
		int passwordHash = Math.abs(rand.nextInt());
		int type = rand.nextInt(101);
		UserType userType;
		if (type < 10) {
			userType = UserType.ADMIN;
		} else {
			if (type < 40) {
				// If user is Company , company id is needed
				userType = UserType.COMPANY;
			}

			else {
				// If user is Customer I must create customer.
				// In tester must be checking and creating of customer if necessary
				userType = UserType.CUSTOMER;
			}
		}

		Company company = null;
		if (userType == UserType.COMPANY) {
			company = ((Company) getRandom(listOfCompanies));
		}
		return new User(username, passwordHash, userType, company);
	}

	public Customer generateRandomCustomer(User user) {
		//email "some@go.go" will be split to "some" and "go.go"
		String[] parts = user.getUsername().split("@");
		String name = parts[0]+" "+getRandom(mockSecondNames);
		String phone = getRandom(mockPhones);
		String address = getRandom(mockAddresses);
		int amountOfKids = rand.nextInt(5);
		boolean isMarried = rand.nextBoolean();
		return new Customer(name, phone, amountOfKids, address, isMarried, user);
	}

	public Coupon generateRandomCoupon(List<Company> listOfCompanies) {
		float price = rand.nextFloat() * 1000;
		int amount = rand.nextInt(40) + 10;
		Category category = Category.values()[rand.nextInt(Category.values().length)];
		// January 15% Promotion

		String title1[] = { "Black","White","Green","Yellow","Red","Blue","Orange","Brown","Magenta","Grey","Big","Small" };

		String title2[] = { "Chair","Table","Stool","Bed","Sofa","Cupboard","Shelf","Couch","Wardrobe","Commode","Armchair","Desk" };

		

		String title = getRandom(title1) + " " + getRandom(title2);

		String descr[] = { "", null, "simple"+title, "No description", "Test",
				"Just coupon" };
		String description = getRandom(descr);
		long range = TimeUnit.DAYS.toMillis(5);
		long overlapRange = TimeUnit.DAYS.toMillis(2);
		long goToPast = (long) (rand.nextDouble() * range);
		long goToFuture = (long) (rand.nextDouble() * range);

		Date startDate = new Date(System.currentTimeMillis() - goToPast + overlapRange);
		Date endDate = new Date(System.currentTimeMillis() + goToFuture - overlapRange);
		Company company = ((Company) getRandom(listOfCompanies));
		return new Coupon(title, price, company, category, amount, startDate, endDate, description);
	}

	public Company generateRandomCompany() {

		String name = getRandom(mockCompanyNames);
		String address = getRandom(mockAddresses);
		String phone = getRandom(mockPhones);

		return new Company(name, address, phone);
	}

	public Purchase generateRandomPurchase(List<Coupon> listOfCoupons, List<Customer> listOfCustomers) {

		Coupon coupon = ((Coupon) getRandom(listOfCoupons));
		Customer customer = ((Customer) getRandom(listOfCustomers));
		int amount = rand.nextInt(20) + 1;
		return new Purchase(coupon, customer, amount);
	}
	

	private Object getRandom(List<?> list) {
		return list.get(rand.nextInt(list.size()));
	}

	private String getRandom(String[] array) {
		Random rand = new Random();
		return array[rand.nextInt(array.length)];
	}
	
	
}
