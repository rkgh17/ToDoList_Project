package com.hjh.todolist.list;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long>{

	List<TodoList> findAllByMemberid(Long memberid);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE todo_list SET isdone = :state WHERE listid = :listid", nativeQuery = true)
	void updateList(@Param("state")boolean state, @Param("listid")Long listid);
	
	@Query(value = "SELECT * from todo_list WHERE memberid = :memberid AND isdone = 0", nativeQuery = true)
	List<TodoList> AllList(@Param("memberid")Long memberid);
}
