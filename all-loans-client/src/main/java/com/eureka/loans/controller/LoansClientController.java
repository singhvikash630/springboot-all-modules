package com.eureka.loans.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eureka.loans.models.LoanType;
import com.eureka.loans.service.LoansClientService;

@RestController
@RequestMapping("/loans")
public class LoansClientController {

	@Autowired
	LoansClientService service;

	@GetMapping("all")
	public List<LoanType> getLoans() {
		return service.getLoans();
	}

}
