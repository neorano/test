package com.maxim.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxim.enums.Category;
import com.maxim.exception.ApplicationException;

@RestController
@RequestMapping("/server")
public class ServerDataApi {
	@GetMapping
	public Category[] getCategories() throws ApplicationException {
		return Category.values();
	}
}
