package com.eureka.loans.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanType {
	private long id;
	private String type;
	private int roi;
	private String bankName;
}
