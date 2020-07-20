package com.eureka.homeloan.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eureka.homeloan.dao.HomeLoanRepositry;
import com.eureka.homeloan.entity.LoanType;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class HomeLoanService {

	@Autowired
	HomeLoanRepositry repositry;

	public LoanType getLoanType(long id) {
		return repositry.findById(id).orElse(null);
	}
	
	@HystrixCommand(fallbackMethod = "saveLoanType_Fallbacks")
	public LoanType saveLoanType(LoanType type) {
		return repositry.save(type);
	}
	
	@SuppressWarnings("unused")
	private String saveLoanType_Fallbacks(LoanType type) {

		System.out.println("HomeLoan Service is down!!! fallback route enabled...");

		return "CIRCUIT BREAKER ENABLED!!! No Response From HomeLoan Service at this moment. "
				+ " Service will be back shortly - " + new Date();
	}

}
