package com.maxim.interfaces;

import org.springframework.data.repository.CrudRepository;

import com.maxim.entities.Customer;

public interface ICustomersDAO extends CrudRepository<Customer, Long> {
}