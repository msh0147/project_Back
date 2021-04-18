package com.example.service;

import com.example.entity.User;

/**
 * @description	
 * @author		MinSeongHyeon
 * @since		2021/04/16
 */
public interface UserService {
	
	//회원 가입
	Object join(User inputData) throws Exception;
}
