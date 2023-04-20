package com.hjh.todolist.service;

import org.springframework.stereotype.Service;
import java.util.List;

import com.hjh.todolist.list.TodoList;
import com.hjh.todolist.list.TodoListDTO;
import com.hjh.todolist.list.TodoListRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoListService {
	
	private final TodoListRepository todoListRepository;
	
	public TodoListDTO createList(TodoListDTO todoListDTO) {
		
		TodoList todolist = todoListDTO.toTodoList();
		todoListRepository.save(todolist);
		
		return null;
		
	}
	
	public String findList(Long memberid) {
		
		System.out.println("id로 데이터찾기 test");
		
		List todolist = todoListRepository.findAllByMemberid(memberid);
		
//		System.out.println(todolist.get(0));
		
		return null;
	}

}
