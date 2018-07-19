
package com.test.service.services.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsFilter implements Filter {

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {		
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws IOException, ServletException {
		
	 
	        HttpServletRequest request = (HttpServletRequest) req;
	        System.out.println("CORSFilter HTTP Request: " + request.getMethod());
	 
	        // Authorize (allow) all domains to consume the content
	        ((HttpServletResponse) res).addHeader("Access-Control-Allow-Origin", "*");
	        ((HttpServletResponse) res).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");
	 
	        HttpServletResponse resp = (HttpServletResponse) res;
	 
	        // For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS handshake
	        if (request.getMethod().equals("OPTIONS")) {
	            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
	            return;
	        }
	 
	        // pass the request along the filter chain
	        chain.doFilter(request, res);

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
