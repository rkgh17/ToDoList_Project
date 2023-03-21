package com.hjh.todolist.auth;

import lombok.Getter;

@Getter
public class AuthInfo {

    private Long id;
    private String role_type;
    private String nickname;

    public AuthInfo(Long id, String role_type, String nickname) {
        this.id = id;
        this.role_type = role_type;
        this.nickname = nickname;
    }
}