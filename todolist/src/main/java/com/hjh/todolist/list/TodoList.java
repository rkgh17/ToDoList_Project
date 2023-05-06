package com.hjh.todolist.list;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

import com.hjh.todolist.member.Member;
import com.hjh.todolist.member.RoleType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Builder
@DynamicUpdate
public class TodoList {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listid;
    
    @Column(nullable = false)
    private Long memberid;
    
    @Column(nullable = false)
    private String deadline;
    
    @Column(nullable = false)
    private String startline;
    
    @Column(nullable = false)
    private String todo;
    
    @Column(columnDefinition = "boolean default false")
    private boolean isdone;
    
    @Column(nullable = true)
    private String finishdate;
    
    @Builder
    public TodoList(Long memberid, String deadline, String startline, String todo) {
    	this.memberid = memberid;
    	this.deadline = deadline;
    	this.startline = startline;
    	this.todo = todo;
    }

}
