package com.zw.framework.filter;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AjaxAndFormAuthenticationFilter extends FormAuthenticationFilter {

	private static final Logger log = LoggerFactory.getLogger(AjaxAndFormAuthenticationFilter.class);

	
	/**
	 * 所有请求都会经过的方法。
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		if (isLoginRequest(request, response)) {
			if (isLoginSubmission(request, response)) {
				if (log.isTraceEnabled()) {
					log.trace("Login submission detected.  Attempting to execute login.");
				}
				return executeLogin(request, response);
			}
			if (log.isTraceEnabled()) {
				log.trace("Login page view.");
			}

			return true;
		} else {

			if (log.isTraceEnabled()) {
				log.trace("Attempting to access a path which requires authentication.  Forwarding to the "
						+ "Authentication url [" + getLoginUrl() + "]");
			}
			if (!"XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {
				// 不是ajax请求
				saveRequestAndRedirectToLogin(request, response);
			} else {
				HttpServletRequest req = (HttpServletRequest) request;
				HttpServletResponse res = (HttpServletResponse) response;
				String loginUrl = req.getContextPath() + "/login/display";
				res.addHeader("sessionstatus", "timeOut");
				res.addHeader("loginPath", loginUrl);				
				res.setCharacterEncoding("UTF-8");
				PrintWriter out = res.getWriter();
				out.println("{message:'login'}");
				out.flush();
				out.close();
			}
			return false;
		}
	}
}
