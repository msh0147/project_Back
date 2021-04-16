package com.example.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.security.MyFilter;

/**
 * @description jwt인증에 필요한 필터(com.example.security.MyFilter.java) 환경설정을 위한 클래스
 * @author 		MinSeongHyeon
 * @since		2021/04/15
 */

@Configuration
public class FilterConfig {
	
	@Bean
	FilterRegistrationBean<MyFilter> filter() {
		FilterRegistrationBean<MyFilter> bean  = new FilterRegistrationBean<>(new MyFilter());
		//지정 url
		bean.addUrlPatterns("/*");
		//1순위 지정
		bean.setOrder(0);
		return bean;
		
	}
}
