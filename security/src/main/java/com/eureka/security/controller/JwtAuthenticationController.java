package com.eureka.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eureka.security.model.JwtRequest;
import com.eureka.security.model.JwtResponse;
import com.eureka.security.services.JwtUserDetailsService;
import com.eureka.security.util.JwtTokenUtil;

@RestController
@RequestMapping("/security")
public class JwtAuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest,
			@RequestHeader(value = "Authorization", required = false) String authorization) throws Exception {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		UserDetails userPrincipal = null;
		String token = null;

		if (principal != null && principal instanceof UserDetails) {
			userPrincipal = (UserDetails) principal;
		}

		String username = userPrincipal == null ? null : userPrincipal.getUsername();
		boolean isAuthenticationRequired = false;
		if (authenticationRequest != null && authenticationRequest.getUsername() != null
				&& !authenticationRequest.getUsername().equals(username)) {
			username = authenticationRequest.getUsername();
			isAuthenticationRequired = true;
		}
		if (isAuthenticationRequired) {
			authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
			final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
			token = jwtTokenUtil.generateToken(userDetails);

		} else {
			if (authorization.startsWith("Bearer ")) {
				System.out.println("Already Authorized");
			}
		}
		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
