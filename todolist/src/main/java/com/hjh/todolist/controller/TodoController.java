package com.hjh.todolist.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
		
//		System.out.println(map);
		
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
	
	// 
	@PostMapping("donetodo")
	public ResponseEntity<Void> doneTodo(@RequestBody Map<String, Object> map){
		System.out.println(map);
		return null;
	}
	
	// 화면에 TodoList를 띄워주기 위해 select쿼리를 수행하여 보내줌
	@PostMapping("/list")
	public String list(@RequestBody Map<String, Object> map) {	
	
		// 리스트를 찾기 위한 memberid
		Long memberid = Long.parseLong((String) map.get("sub"));
		
//		System.out.println("리스트 테스트 - member id : " +  memberid);
		
		
		
		return todoListService.findList(memberid);
	}
	

}
