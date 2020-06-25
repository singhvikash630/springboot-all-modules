package com.eureka.gateway.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.eureka.gateway.constant.Constants;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class PreFilter extends ZuulFilter {

	@Value("${security.login.url}")
	private String SecurityLoginUrl;

	List<String> skippedURLs = new ArrayList<String>(Arrays.asList("/security/login", "/security/auth/logout"));

	@Autowired
	RestTemplate restTemplate;

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		System.out.println("PreFilter: "
				+ String.format("%s request to %s ", request.getMethod(), request.getRequestURL().toString()));

		String requestURI = request.getRequestURI();

		String method = request.getMethod();

		System.out.println("++++Request URI: " + requestURI);

		String xRequestTypeHeader = "X-Request-Type";
		String requestTypeParamter = request.getParameter(Constants.REQUEST_TYPE);
		String xRequestTypeHeaderValue = request.getHeader(xRequestTypeHeader);
		boolean isSecuritySkip = false;

		String refreshToken = request.getHeader("refresh-token");
		System.out.println(
				"Request Method : " + request.getMethod() + " Request URL : " + request.getRequestURL().toString());
		System.out.println("++++REQUEST TYPE HEADER VALUE : " + xRequestTypeHeaderValue);

		if (!skippedURLs.contains(requestURI) && !isSecuritySkip) {

			System.out.println("++++++REACHED HERE: INSIDE IF");
			String header = request.getHeader("Authorization");

			System.out.println("Header: " + header);
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", header);
			headers.set("source", "filter");
			headers.set("refresh-token", refreshToken);
			headers.setContentType(MediaType.APPLICATION_JSON);
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			final HttpEntity<MultiValueMap<String, String>> postEntity = new HttpEntity<MultiValueMap<String, String>>(
					map, headers);
			ResponseEntity<Object> entity = null;
			try {
				entity = restTemplate.postForEntity(SecurityLoginUrl, postEntity, Object.class);
				/*
				 * Object body = entity.getBody(); HttpHeaders responseHeader =
				 * entity.getHeaders();
				 * 
				 * List<String> newTokenList = responseHeader.get("token");
				 * 
				 * if(!CollectionUtils.isEmpty(newTokenList)) { String token =
				 * newTokenList.get(0);
				 * 
				 * ctx.addZuulResponseHeader("token", token);
				 * ctx.addZuulRequestHeader("Authorization", "Bearer " + token); }
				 * System.out.println("came back");
				 */
			} catch (HttpClientErrorException ex) {
				// get your token from request context and send it to auth service via rest
				// template
				System.out.println("++++++REACHED HERE: Exception Occured");

				int rawStatusCode = ex.getRawStatusCode();
				if (rawStatusCode == HttpStatus.UNAUTHORIZED.value() || rawStatusCode == HttpStatus.FORBIDDEN.value()) {
					System.out.println("++++++REACHED HERE: unaouthorized/forbidden: " + rawStatusCode);
					ctx.setSendZuulResponse(false); // This makes request not forwarding to micro services
					ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
					ctx.setResponseBody("Invalid Access");
					ctx.getResponse().setContentType("application/json");
				} else {
					System.out.println("++++++REACHED HERE: preparing auth response: " + rawStatusCode);
				}
				ex.printStackTrace();
			}

			ctx.addZuulRequestHeader("source", "GATEWAY");
			ctx.addZuulRequestHeader("X-Request-Type", "COSMOS");

		}
		return null;
	}

}
