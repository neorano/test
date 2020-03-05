package com.maxim.interfaces;

import org.springframework.data.repository.CrudRepository;

import com.maxim.entities.User;

public interface IUsersDAO extends CrudRepository<User, Long> {
	User findByUsername(String username);
}