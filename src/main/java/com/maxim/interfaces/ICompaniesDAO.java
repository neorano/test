package com.maxim.interfaces;


import org.springframework.data.repository.CrudRepository;

import com.maxim.entities.Company;

public interface ICompaniesDAO extends CrudRepository<Company, Long> {

	Company findByName(String name);
	
}