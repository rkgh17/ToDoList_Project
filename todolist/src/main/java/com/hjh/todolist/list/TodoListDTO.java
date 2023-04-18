package com.hjh.todolist.list;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoListDTO {
	
	private Long listid;
	private Long memberid;
	private String deadline;
	private String startline;
	private String todo;

	
	public TodoList toTodoList() {
		return TodoList.builder()
					.memberid(memberid)
					.deadline(deadline)
					.startline(startline)
					.todo(todo)
					.build();
	}
	

}
