package com.example.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.repository.UserRepository;

import lombok.RequiredArgsConstructor;


/**
 * @description	
 * @author		minseonghyun
 * @since		2021/04/15
 * @apiNote		from com.example.security.JwtAuthenticationFilter.java
 */
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("================================================loadUserByUsername : " + username);
		User user = userRepository.findByUsername(username);
		return new PrincipalDetails(user);
	}

}
