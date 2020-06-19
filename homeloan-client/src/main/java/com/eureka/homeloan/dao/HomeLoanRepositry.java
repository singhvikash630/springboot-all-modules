package com.eureka.homeloan.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eureka.homeloan.entity.LoanType;

public interface HomeLoanRepositry extends JpaRepository<LoanType, Long>{

}
