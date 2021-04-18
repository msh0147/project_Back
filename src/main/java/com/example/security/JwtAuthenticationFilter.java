package com.example.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

/**
 * @description PrincipalDetailsService를 호출하기 위한 인증 필터 클래스
 * @author		MinSeongHyeon
 * @since		2021/05/14
 * @apiNote		/project/login
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;
	
	/**
	 * @description /login 요청을 하면 로그인 시도를 위해 실행되는 인증 메소드
	 * @param		json 타입 로그인 요청 정보가 담긴 HttpRequest / User user entity
	 * @return		Authentication객체의 인증된 jwt token을 백엔드 세션으로 return	
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
//		System.out.println("================================================JwtAuthenticationFilter : LoginProcessing");
		
		try {
//			System.out.println(request.getInputStream());
			ObjectMapper om = new ObjectMapper();
			User user = om.readValue(request.getInputStream(), User.class);
//			System.out.println("================================================Login inputData : " + user);
			
			//로그인 정보 토큰 생성 loadByUsername실행() -> username & password저장
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
			//정상이면 토큰 생성 & 로그인 성공(아이디와 패스워드 일치)
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
//			System.out.println("================================================authentication : " + authentication);
			
			//백엔드 세션영역에 로그인 정보 저장(토큰만 사용할 거면 굳이 안만들어도 됨,,, back에서 시큐리티 권한 관리 사용하기 위해 만들은 거)
			PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
//			System.out.println("================================================authenticationSuccessLogin : " + principalDetails);
//			System.out.println("================================================Logined ID : " + principalDetails.getUser().getUsername());
			
			return authentication;
		}
		catch (IOException e) {	
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @description attemptAuthentication실행 후 정상 로그인 완료 후 후처리 하는 메소드이다.
	 * @param		Authentication authResult로 인증이 완료된 계정의 User Entity이다
	 * @Return		Jwt토큰 생성 후 클라이언트에 return
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
//		System.out.println("================================================successfulAuthentication : Login Success!");
//		System.out.println("================================================Login Result : " + authResult);
		PrincipalDetails principalDetails = (PrincipalDetails)authResult.getPrincipal();
		
		//Hash 암호방식 => HMAC512는 서버만 아는 secret값이 있어야함
		String jwtToken = JWT.create()
			.withSubject(principalDetails.getUsername()) //토큰 명
			.withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME)) //만료 기간(10분으로 지정)
			//토큰에 담을 값 지정
			.withClaim("id", principalDetails.getUser().getId())
			.withClaim("username", principalDetails.getUser().getUsername())
			.sign(Algorithm.HMAC512(JwtProperties.SECRET));//내 서버만 아는 고유한 SECRET값 지정
		
//		System.out.println("================================================jwtToken : " + jwtToken);
		
		response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken); //Bearer방식 사용
	}
}
