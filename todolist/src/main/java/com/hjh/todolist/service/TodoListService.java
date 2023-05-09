package com.hjh.todolist.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.hjh.todolist.list.TodoList;
import com.hjh.todolist.list.TodoListDTO;
import com.hjh.todolist.list.TodoListDTO.TodoListDTOBuilder;
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
		
//		System.out.println("id로 데이터찾기 test");
		
//		List<TodoList> todolist = todoListRepository.findAllByMemberid(memberid);
		List<TodoList> todolist = todoListRepository.AllList(memberid);
		
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
	
	// TodoList 완료체크 - 취소
	public Long updateList(boolean state, Long listId) {
		
		todoListRepository.updateList(state, listId);
//		System.out.println("DB Update 완료");
		
		return null;
		
	}
	
	// TodoList 삭제
	public Long deleteList(Long listId) {
		
		todoListRepository.deleteById(listId);
		
		return null;
	}

}
