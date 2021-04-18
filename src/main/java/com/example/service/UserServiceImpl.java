package com.example.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * @description	회원 정보에 관한 비즈니스 로직을 수행하는 Service Model
 * @author		MinSeongHyeon
 * @since		2021/04/16
 */

@Service
@Transactional(rollbackOn = RuntimeException.class)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final BCryptPasswordEncoder bcpe;
	
	@Autowired
	UserRepository userRepository;

	/**
	 * @description 신규 회원 정보를 
	 * @param		User user
	 * @return		Object obj로 request 수행 결과를 UserControler.join()에 return 
	 * @exception	
	 */
	public Object join(User inputData) throws Exception {
		try {
			Object obj = userRepository.save(inputData);
			return obj;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
