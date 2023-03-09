package com.hjh.todolist.api.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired 
	private MemberRepository memberRepository;
	
	// 회원 가입
	public String SignUp(Map<String, Object> map) {
		
		// 폼 전송값 확인
//		System.out.println(map);
		
		// map 자료찾기
//		System.out.println(map.get("username"));
		
		// 비밀번호 암호화
		String encodePW = passwordEncoder.encode((CharSequence) map.get("passwd"));
		map.put("passwd", encodePW);
//		System.out.println(map.get("passwd"));		
		
		// 아이디를 db에 넣기 전에, 중복검사를 한다.
		if(memberRepository.selectXml(map) == null) {
			memberRepository.insertXml(map);
			return null;
		}
		else {// id 중복
			return "id중복";
		}
	}
	
	

}
