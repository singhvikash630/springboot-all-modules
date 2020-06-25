package com.eureka.gateway.filter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class ErrorFilter extends ZuulFilter {
	@Override
	public String filterType() {
		return "error";
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

		HttpServletResponse response = RequestContext.getCurrentContext().getResponse();
		System.out.println("Error Filter: " + String.format("response's content tyoe is %s", response.getStatus()));
		return null;

	}

}
