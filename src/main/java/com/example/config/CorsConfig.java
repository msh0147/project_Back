package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @description	CORS환경설정을 위한 클래스
 * @author 		MinSeongHyeon
 * @since		2021/04/15
 */
@Configuration
public class CorsConfig {
	
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		
		//allow json
		config.setAllowCredentials(true); 
		//all ip
		config.addAllowedOrigin("*"); 
		//all header
		config.addAllowedHeader("*");
		//all method
		config.addAllowedMethod("*"); 
		//register corsConfig
		source.registerCorsConfiguration("/poject/**", config);
		
		return new CorsFilter(source);
	}
}
