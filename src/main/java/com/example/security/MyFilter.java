package com.example.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description JWT인증을 위한 필터 클래스
 * @author		MinSeongHyun
 * @since		2021/04/15
 * @apiNote		GET 과 POST를 제와한 request는 필터 적용
 * 				POST의 경우 header KEY : Authorization / value : "Bearer "
 */
public class MyFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		req.setCharacterEncoding("UTF-8");
		if(req.getMethod().equals("POST")) {
			
//			System.out.println("Requested Method : POST");
			String headerAuth = req.getHeader("Authorization");
//			System.out.println("Request Header : " + headerAuth);
			
			if(headerAuth.startsWith("Bearer")) {
				chain.doFilter(req, res);
			}
			else {
//				System.out.println("unAuthorized Request!!");
				PrintWriter out = res.getWriter();
				out.println("UnAuthorized Request");
			}	
		} 
		else if (req.getMethod().equals("GET")) {
//			System.out.println("Requested Method : GET");
			chain.doFilter(req, res);
		}
//		System.out.println("================================================Filter================================================");
	}
}
