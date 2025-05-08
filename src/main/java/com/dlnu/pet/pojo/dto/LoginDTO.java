package com.dlnu.pet.pojo.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String loginType;
    private String phone;
    private String password;
    private String code;
} 
