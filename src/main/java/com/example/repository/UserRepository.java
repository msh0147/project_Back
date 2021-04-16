package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.User;

/**
 * @description	MySQL User entity 매핑을 위한 Repository 
 * @author		MinSeongHyeon
 * @since		2021/04/15
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByUsername(String username);
}
