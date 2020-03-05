package com.maxim.filters;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.maxim.cache.CacheController;
import com.maxim.serverinternal.UserData;
//@Component // Comment this all to disable
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class LoginFilter implements Filter {

	@Autowired
	private CacheController cacheController;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;

		// Exclude : "Register", "Login" (something with req...)
	
		String pageRequested = req.getRequestURL().toString();

		if (pageRequested.endsWith("/login")) {
			
			chain.doFilter(request, response);
			return;
		}

		if (pageRequested.endsWith("/customers") && 
				req.getMethod().toString().equals("POST")) {
			chain.doFilter(request, response);
			return;
		}
//		Enumeration<String> s  =req.getParameterNames();
//		  while ( s.hasMoreElements()) {
//		       System.out.println(s.nextElement());
//		       }
//		
	String token = req.getHeader("Authorization");


	//String token = request.getParameter("token");

	System.out.println(token);
		UserData userData = (UserData) cacheController
				.get(token);
		if (userData != null) {
			//adding data to request
			request.setAttribute("userData", userData);
			// U're logged in - all is good
			// Move forward to the next filter in chain
			chain.doFilter(request, response);
			return;
		}

		HttpServletResponse res = (HttpServletResponse) response;
		// U're not logged in
		res.setStatus(401);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
