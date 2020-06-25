package com.eureka.homeloan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eureka.homeloan.entity.LoanType;
import com.eureka.homeloan.service.HomeLoanService;

@RestController
@RequestMapping("/homeloan")
public class HomeLoanController {

	@Autowired
	HomeLoanService service;

	@GetMapping(value = "/get/{id}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public LoanType getLoanType(@PathVariable long id) {
		return service.getLoanType(id);
	}

	@PostMapping("/create")
	public LoanType saveLoanType(@RequestBody LoanType type) {
		return service.saveLoanType(type);
	}
}
