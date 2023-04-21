package com.hjh.todolist.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

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
	
	// 데이터를 찾고, json으로 변환
	public String findList(Long memberid) {
		
		System.out.println("id로 데이터찾기 test");
		
		List<TodoList> todolist = todoListRepository.findAllByMemberid(memberid);
		
		JSONArray jsonArray = new JSONArray();
		
		for(TodoList todo : todolist) {
			JSONObject json = new JSONObject();
			json.put("listid", todo.getListid());
			json.put("startline", todo.getStartline());
			json.put("deadline", todo.getDeadline());
			json.put("todo", todo.getTodo());
			json.put("isdone", todo.isIsdone());
			jsonArray.add(json);
		}

	
//		System.out.println(todolist.get(1).getTodo());
		
		return jsonArray.toJSONString();
	}

}
