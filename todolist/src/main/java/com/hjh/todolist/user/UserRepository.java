package com.hjh.todolist.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
	
	// 해당 쿼리가 수행될 때, Lazy가 아니고 Eager 조회로 authorites 정보를 가져옴
	@EntityGraph(attributePaths = "authorities")
	Optional<User> findOneWithAuthoritiesByUsername(String username); // username을 기준으로 User정보와, 권한 정보를 같이 가져오는 메서드
	
}
