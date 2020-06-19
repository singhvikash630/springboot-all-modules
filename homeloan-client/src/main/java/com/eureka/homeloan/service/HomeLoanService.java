package com.eureka.homeloan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eureka.homeloan.dao.HomeLoanRepositry;
import com.eureka.homeloan.entity.LoanType;

@Service
public class HomeLoanService {

	@Autowired
	HomeLoanRepositry repositry;

	public LoanType getLoanType(long id) {
		return repositry.findById(id).orElse(null);
	}
	
	public LoanType saveLoanType(LoanType type) {
		return repositry.save(type);
	}

}
