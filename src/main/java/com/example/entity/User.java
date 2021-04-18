package com.example.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

/**
 * @description	회원(User)정보를 관리하는 UserEntity
 * @author 		MinSeongHyeon
 * @since		2021/04/15
 * @comment		USER테이블에 해당
 */

@Entity
@Data
public class User {
	//회원 인덱스
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	//회원아이디
	private String username;
	
	//회원비밀번호
	private String password;
	
	//회원새비밀번호
	@Transient
	private String newPassword;
	
	//회원권한(기본값 USER)
	private String roles = "USER";
	@Transient
	public List<String> getRoleList() {
		if(this.roles.length() > 0) {
			return Arrays.asList(this.roles.split(","));
		}
		return new ArrayList<>();
	}

}
