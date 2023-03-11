//package com.hjh.todolist.api.member;
//
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//@RestController
//public class MemberController {
//
//	private final MemberService memberService;
//	
//	// 회원가입 api
//	@PostMapping("/api/signup")
//	public String signupForm(@RequestBody Map<String, Object> map) {
//		
//		System.out.println(map);
//
//		return memberService.SignUp(map);
//		
//		
//	}
//	
//	// 로그인 api
//	@PostMapping("/api/login")
//	public void loginForm(@RequestBody Map<String, Object> map) {
//		System.out.println(map);
//	}
//	
//	// 로그인 api 테스트
//
//}
