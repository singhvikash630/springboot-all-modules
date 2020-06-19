package com.eureka.homeloan.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "loantype")
public class LoanType {
	@Id
	@GeneratedValue
	private long id;
	private String type;
	private int roi;
	private String bankName;
}
