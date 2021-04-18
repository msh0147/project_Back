package com.example.security;

/**
 * @description	security properties를 지정하기 위한 인터페이스
 * @author 		MinSeongHyeon
 * @since		2021/04/18
 */
public interface JwtProperties {
	//HMAC SECRET값
	String SECRET = "MIN";
	//토큰 시간
	int EXPIRATION_TIME = 60000 * 10;
	//토큰 value 시작값
	String TOKEN_PREFIX = "Bearer ";
	//토큰 key값
	String HEADER_STRING = "Authorization";
}
