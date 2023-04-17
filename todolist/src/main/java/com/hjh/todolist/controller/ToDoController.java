package com.hjh.todolist.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ToDoController {
	
	@PostMapping("/createtodo")
	public ResponseEntity<Void> createTodo(){
		return null;
		
	}

}
