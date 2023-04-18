package com.hjh.todolist.service;

import org.springframework.stereotype.Service;

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

}
