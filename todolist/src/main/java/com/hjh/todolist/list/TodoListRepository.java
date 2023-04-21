package com.hjh.todolist.list;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long>{

	List<TodoList> findAllByMemberid(Long memberid);
}
