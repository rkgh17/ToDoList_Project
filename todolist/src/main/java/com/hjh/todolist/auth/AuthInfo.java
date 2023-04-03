package com.hjh.todolist.auth;

import com.hjh.todolist.member.RoleType;

import lombok.Getter;

@Getter
public class AuthInfo {

    private Long id;
    private RoleType role_type;
    private String nickname;

    public AuthInfo(Long id, RoleType roleType, String nickname) {
        this.id = id;
        this.role_type = roleType;
        this.nickname = nickname;
    }
}