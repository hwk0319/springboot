package com.it.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.it.po.UserInfo;


/**
 * 实现Filter接口，实现Filter方法
 * @author Administrator
 *
 */
public class MyFilter implements Filter{
//	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
//		HttpServletRequest httpRequest = (HttpServletRequest) request;  
//        HttpServletResponse httpResponse = (HttpServletResponse) response;  
//        String url=httpRequest.getRequestURI();  
//        logger.info("request url :"+url);

        chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
