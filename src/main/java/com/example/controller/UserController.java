package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.User;
import com.example.service.UserServiceImpl;
import com.example.util.Response;

import lombok.RequiredArgsConstructor;

/**
 * @description	회원 정보를 관리하는 Controller클래스
 * @author		MinSeongHyeon
 * @since		2021/04/16
 */

@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	UserServiceImpl userService;
	
	private final Response response = new Response();
	
	/**
	 * @description	회원 가입을 위해 /api/join으로부터 회원 가입 입력 정보를 받는 ßRestController메소드
	 * @param 		User user로 회원 정보와 관련된 회원 entity이다
	 * @return		Response로 요청 작업 수행 결과를 front에 반환
	 * @exception	
	 */
	@PostMapping("/join")
	@ResponseStatus(HttpStatus.OK)
	public Response join(@RequestBody User inputData) throws Exception {
//		System.out.println(inputData.toString());
		try {
			Object ret = userService.join(inputData);
			if(ret != null) {
				response.setData(ret);
				response.setResponse("success");
				response.setMessage("회원 가입 성공");
			}
			else {
				response.setData(ret);
				response.setResponse("fail");
				response.setMessage("DB에 입력한 회원 가입 정보 저장 실패");
			}
		} 
		catch (Exception e) {
			response.setData(e.getMessage());
			response.setResponse("fail");
			response.setMessage("Problems from UserController.join()");
		}		
		return response;
	}
	
}
