package com.example.filter.requestFilter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Component;

@Component
public class RequestFilter implements Filter{
	
	@Override
	   public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterchain) 
			      throws IOException, ServletException {
		
		System.out.println("Inside Filter...");
			      filterchain.doFilter(request, response);
			   }

}
