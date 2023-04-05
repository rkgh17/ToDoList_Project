package com.hjh.todolist.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
	Optional<Member> findByEmail(String email);
	boolean existsByEmail(String email);
	
	// refreshtoken생성시 memberid필요
	@Query(value = "SELECT id FROM member WHERE email = ?", nativeQuery = true)
	Long NativeQuery_findID(String email);
	
	@Query(value = "SELECT nickname FROM member WHERE email = ?", nativeQuery = true)
	String NativeQuery_findNickname(String email);
	
}
