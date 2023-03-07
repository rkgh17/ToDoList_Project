package com.hjh.todolist.api.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@Autowired private MemberRepository memberRepository;
	
	// 회원가입 api
	@PostMapping("/api/signup")
	public String signupForm(@RequestBody Map<String, Object> map) {
		
		// 폼 전송값 확인
//		System.out.println(map);
		
		// map 자료찾기
//		System.out.println(map.get("username"));
		
		
		// 아이디를 db에 넣기 전에, 중복검사를 한다.
		if(memberRepository.selectXml(map) == null) {
			memberRepository.insertXml(map);
			return null;
		}
		else {// id 중복
			return "id중복";
		}
		
	}
	
	// 로그인 api
	@PostMapping("/api/login")
	public void loginForm(@RequestBody Map<String, Object> map) {
		System.out.println(map);
	}

}
