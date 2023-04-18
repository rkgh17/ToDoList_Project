package com.hjh.todolist.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjh.todolist.list.TodoListDTO;
import com.hjh.todolist.service.TodoListService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TodoController {
	
	private final TodoListService todoListService;
	
	
	@PostMapping("/createtodo")
	public ResponseEntity<Void> createTodo(@RequestBody Map<String, Object> map){
		
		System.out.println(map);
		
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		Long memberid = Long.parseLong((String) map.get("sub"));
		String deadline = (String) map.get("deadline");
		String startline = simpleDateFormat.format(new Date());
		String todo = (String) map.get("todo");

		new TodoListDTO();
		TodoListDTO todolistDTO = TodoListDTO.builder()
										.memberid(memberid)
										.deadline(deadline)
										.startline(startline)
										.todo(todo)
										.build();
		
		todoListService.createList(todolistDTO);
	
		
		return null;
		
	}

}
