package com.hjh.todolist.api;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@ResponseBody
	@PostMapping("/api/signup")
	public void signupForm(@RequestBody Map<String, Object> map) {
		
		// 폼 전송값 확인
		System.out.println(map);
		
		// map 자료찾기
		System.out.println(map.get("name"));
	}

}
