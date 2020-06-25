package com.eureka.loans.service;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.eureka.loans.models.LoanType;

@Service
public class LoansClientService {

	@Autowired
	RestTemplate restTemplate;

	@Value("${homeloan.get.url}")
	private String homeLoanUrl;

	public List<LoanType> getLoans(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		headers.set("source", "filter");
		headers.setContentType(MediaType.APPLICATION_JSON);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		final HttpEntity<MultiValueMap<String, String>> postEntity = new HttpEntity<MultiValueMap<String, String>>(map,
				headers);

		ResponseEntity<LoanType> homeLoan = restTemplate.exchange(homeLoanUrl, HttpMethod.GET, postEntity,
				LoanType.class);
		return Arrays.asList(homeLoan.getBody());
	}

}
