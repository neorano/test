package com.maxim.contollers;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.maxim.beans.UserLoginDetails;
import com.maxim.beans.UserLoginToken;
import com.maxim.cache.CacheController;
import com.maxim.entities.User;
import com.maxim.enums.ErrorTypes;
import com.maxim.enums.UserType;
import com.maxim.exception.ApplicationException;
import com.maxim.interfaces.IUsersDAO;
import com.maxim.serverinternal.UserData;
@Controller
public class UsersController {
	@Autowired
	private IUsersDAO usersDAO;
	@Autowired
	private CacheController cacheController;
	@Autowired
	private CustomersController customersController;
	private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
	private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

	public UserLoginToken login(UserLoginDetails loginDetails) throws ApplicationException {
		
		User user;
		
		int loginDetailsPasswordHash = loginDetails.getPassword().hashCode();
		
		try {
			user = usersDAO.findByUsername(loginDetails.getUsername());
		} catch (Exception e) {
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}
		
		if (user==null) {
			throw new ApplicationException (ErrorTypes.USER_NOT_FOUND,  "User "+loginDetails.getUsername() + " not registered");
		}
		if (user.getPasswordHash()!=loginDetailsPasswordHash) {
			throw new ApplicationException(ErrorTypes.PASSWORD_ERROR, "Wrong password");
		}
		
		UserData userDetailsOnSuccesfulLogin = new UserData(user.getId(), user.getUserType(), user.getCompany());
		String token = generateLoginToken(loginDetails);
		UserLoginToken userLoginToken = new UserLoginToken(token, 
				userDetailsOnSuccesfulLogin.getUserType(),userDetailsOnSuccesfulLogin.getCompany());
		cacheController.put(String.valueOf(token), userDetailsOnSuccesfulLogin);
		return userLoginToken;
		
	}

	private String generateLoginToken(UserLoginDetails loginDetails) {
		
		    byte[] randomBytes = new byte[24];
		    secureRandom.nextBytes(randomBytes);
		    return base64Encoder.encodeToString(randomBytes);
		
	}

	public long createUser(User user) throws ApplicationException {
		createUserValidations(user);
		userValidations(user);
		try {
			User newUser = usersDAO.save(user);
			long id = newUser.getId();
			user.setId(id);
			return id;
		} catch (Exception e) {
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}
	}
	
	public void createUserValidations (User user) throws ApplicationException {
		if (usersDAO.findByUsername(user.getUsername())!=null) {
			throw new ApplicationException(ErrorTypes.NAME_ALREADY_EXIST_ERROR, "User already exist");
		}
	}

	public void userValidations(User user) throws ApplicationException {
		if (user.getUsername()==null){
			throw new ApplicationException(ErrorTypes.INCORRECT_USER, "Username cannot be null");
		}
		if (usersDAO.findByUsername(user.getUsername())!=null) {
			throw new ApplicationException(ErrorTypes.NAME_ALREADY_EXIST_ERROR, "Username already taken");
		}
		userEmailValidation(user.getUsername());
		
	

	}
	
	public void updateUserValidations(User user) throws ApplicationException {
		if (user.getUsername()==null){
			throw new ApplicationException(ErrorTypes.INCORRECT_USER, "Username cannot be null");
		}
		
		userEmailValidation(user.getUsername());
		
	

	}

	private void userEmailValidation(String email) throws ApplicationException {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		if ( !email.matches(regex)) {
			throw new ApplicationException(ErrorTypes.EMAIL_NOT_VALID_ERROR , "Email not valid");
		}
		return;
	}


	public void updateUser(User user) throws ApplicationException {
		userValidations(user);
		try {
			usersDAO.save(user);
		} catch (Exception e) {
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}
	}

	public void deleteUser(long userID) throws ApplicationException {
		try {
			if (usersDAO.findOne(userID).getUserType()==UserType.CUSTOMER) {
				if (customersController.getCustomer(userID)==null) {
					throw new ApplicationException(ErrorTypes.NO_PAIRED_USER_FOR_CUSTOMER, "No paired user");
				}
				customersController.deleteCustomer(userID);
			}
		} catch (Exception e1) {
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}
		try {
			usersDAO.delete(userID);
		} catch (Exception e) {
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}
	}

	public List<User> getAllUsers() throws ApplicationException {
		try {
			return (List<User>) usersDAO.findAll();
		} catch (Exception e) {
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}
	}

	public User getUser(long userID) throws ApplicationException {
		
		try {
			return usersDAO.findOne(userID);
		} catch (Exception e) {
			throw new ApplicationException(ErrorTypes.SQL_ERROR, "CRUD error");
		}
	}

}
