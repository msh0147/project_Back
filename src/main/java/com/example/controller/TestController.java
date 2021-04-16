package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.User;
import com.example.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TestController {
	
	private final BCryptPasswordEncoder bcpe;
	
//	태스트;;
//	@Autowired
//	UserRepository userRep;
//
//	@PostMapping(
//			value = "/api/join",
//			consumes = MediaType.ALL_VALUE,
//			produces = MediaType.APPLICATION_JSON_VALUE)
//	public String join() {
//		User inputData = new User();
//		inputData.setUsername("msh0147");
//		inputData.setPassword("Sh13253409@$");
//		inputData.setRoles("ROLE_ADMIN");
//		inputData.setUsername("msh9208");
//		inputData.setPassword("Sh13253409");
//		inputData.setRoles("ROLE_USER");
//		
//		inputData.setPassword(bcpe.encode(inputData.getPassword()));
//		
//		
//		System.out.println(inputData.toString());
//		
//		userRep.save(inputData);
//		return "res";
//	}
}
