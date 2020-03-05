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

import com.maxim.beans.UserLoginDetails;
import com.maxim.beans.UserLoginToken;
import com.maxim.contollers.UsersController;
import com.maxim.entities.User;
import com.maxim.exception.ApplicationException;
@CrossOrigin
@RestController
@RequestMapping("/users")
public class UsersApi {

	// all functions present

	@Autowired
	private UsersController usersController;

	@PostMapping("/login")
	public UserLoginToken login(@RequestBody UserLoginDetails loginDetails) throws ApplicationException {

		return usersController.login(loginDetails);
	}

	@PostMapping
	public long createUser(@RequestBody User user,ServletRequest request) throws ApplicationException {
		HttpServletRequest req = (HttpServletRequest) request;
		String userPassword = req.getHeader("password");
		user.setPasswordHash(userPassword.hashCode());
		return usersController.createUser(user);

	}

	@PutMapping
	public void updateUser(@RequestBody User user) throws ApplicationException {
		usersController.updateUser(user);
	}

	@GetMapping("{userId}")
	public User getUser(@PathVariable("userId") long id) throws ApplicationException {
		return usersController.getUser(id);
	}

	@DeleteMapping("{userId}")
	public void deleteUser(@PathVariable("userId") long id) throws ApplicationException {
		usersController.deleteUser(id);
	}

	@GetMapping
	public List<User> getAllUsers() throws ApplicationException {
		return usersController.getAllUsers();
	}

}
