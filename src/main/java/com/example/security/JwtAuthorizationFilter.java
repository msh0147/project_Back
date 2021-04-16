package com.example.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.entity.User;
import com.example.repository.UserRepository;

/**
 * @description	인증이 필요한 url접근시 토큰 유효성을 검증하는 클래스
 * @author 		MinSeongHyeon
 * @since		2021/04/15
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private UserRepository userRepository;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
		super(authenticationManager);
		this.userRepository = userRepository;
	}
	
	/**
	 * @description	인증/권한이 필요한 request를 인증하는 메소드
	 * @apiNote		
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		super.doFilterInternal(request, response, chain);
		System.out.println("================================================JwtAuthorizationFilter : 인증이 필요한 request");
		
		String jwtHeader = request.getHeader("Authorization");
		System.out.println("================================================jwtHeader : " + jwtHeader);
		
		//헤더가 없거나 잘못된 경우이면 필터 타고 그냥 빼버리기
		if(jwtHeader == null || !jwtHeader.startsWith("Bearer ")) {
			chain.doFilter(request, response);
			return;
		} 
		else {
			String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
			
			String username = 
				JWT
					.require(Algorithm.HMAC512("MIN"))
					.build()
					.verify(jwtToken)
					.getClaim("username")
					.asString();
			if(username != null) {
				System.out.println("================================================인증된 사용자 request 토큰 정상 인증 완료!");
				User user = userRepository.findByUsername(username);
				
				PrincipalDetails principalDetails = new PrincipalDetails(user);
				
				//jwt토큰 정상 서명을 통해 만든 auth객체
				Authentication authentication = 
					new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
				
				//security내 저장 세션에 authentication정보 저장
				//로그인 완료
				SecurityContextHolder.getContext().setAuthentication(authentication);
				chain.doFilter(request, response);
			}
		}
	}
}
