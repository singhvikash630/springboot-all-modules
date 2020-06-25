package com.eureka.loans.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

	@GetMapping(value = "/all", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<LoanType> getLoans(HttpServletRequest request) {
		return service.getLoans(request);
	}

}
