package com.eureka.loans.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.eureka.loans.models.LoanType;

@Service
public class LoansClientService {

	@Autowired
	RestTemplate restTemplate;

	@Value("${homeloan.get.url}")
	private String homeLoanUrl;

	public List<LoanType> getLoans() {
		LoanType homeLoan = restTemplate.getForObject(homeLoanUrl, LoanType.class);
		return Arrays.asList(homeLoan);
	}

}
