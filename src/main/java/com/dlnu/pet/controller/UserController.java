package com.dlnu.pet.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.dlnu.pet.pojo.dto.LoginDTO;
import com.dlnu.pet.pojo.dto.LogoutDTO;
import com.dlnu.pet.pojo.dto.RegisterDTO;
import com.dlnu.pet.pojo.dto.SendCodeDTO;
import com.dlnu.pet.pojo.vo.Result;
import com.dlnu.pet.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody RegisterDTO dto) {
        return userService.register(dto);
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO dto) {
        return userService.login(dto);
    }

    @PostMapping("/sendCode")
    public Result sendCode(@RequestBody SendCodeDTO dto) {
        return userService.sendCode(dto.getPhone(), dto.getType());
    }

    @PostMapping("/logout")
    public Result logout(@RequestBody LogoutDTO dto) {
        return userService.logout(dto.getToken());
    }
} 
