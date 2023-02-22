package com.hjh.todolist;


import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {
	
	@GetMapping("/api/hello")
	public List<String> Hello(){
		return Arrays.asList("컨트롤러","테스트");
	}

}
