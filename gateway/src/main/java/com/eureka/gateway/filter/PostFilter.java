package com.eureka.gateway.filter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class PostFilter extends ZuulFilter {

	@Override
	public String filterType() {
		return "post";
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
		HttpServletResponse response = RequestContext.getCurrentContext().getResponse();
		System.out.println("Post Filter: " + String.format("response's content type is %s", response.getStatus()));
		return null;
	}

}
