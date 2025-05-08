package com.dlnu.pet.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginVO {
    private String token;
    private Long userId;
    private String userName;
    private String avatar;

    public LoginVO(String token, Long userId, String userName) {
        this.token = token;
        this.userId = userId;
        this.userName = userName;
    }
} 