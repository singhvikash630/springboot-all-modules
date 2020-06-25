package com.eureka.gateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class RouteFilter extends ZuulFilter {

	@Override
	public String filterType() {
		return "route";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {

		HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
		System.out.println("RouteFilter: "
				+ String.format("%s request to %s ", request.getMethod(), request.getRequestURL().toString()));
		return null;
	
	}

}
