package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

import com.example.repository.UserRepository;
import com.example.security.JwtAuthenticationFilter;
import com.example.security.JwtAuthorizationFilter;
import com.example.security.MyFilter;

import lombok.RequiredArgsConstructor;

/**
 * @description	JWT 시큐리티 환경설정을 위한 클래스
 * @author		MinSeongHyeon
 * @since		2021/04/15
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private final CorsFilter corsFilter;
	private final UserRepository userRepository;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(new MyFilter(), SecurityContextPersistenceFilter.class);
		
		http.csrf().disable();
		
		http
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			
			//CorsFilter 등록(Cors 등록 정책에서 허용) -> CrossOrigin은 인증이 필요한 요청은 다 거부됨
			.addFilter(corsFilter)
			
			.formLogin().disable()
			.httpBasic().disable()
			.addFilter(new JwtAuthenticationFilter(authenticationManager()))
			.addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
			.authorizeRequests()			
				.antMatchers("/api/user/**")
					.access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
				.antMatchers("/api/admin/**")
					.access("hasRole('ROLE_ADMIN')")
				.anyRequest()
					.permitAll();
	}
	
}
