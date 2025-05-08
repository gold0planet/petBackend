package com.dlnu.pet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dlnu.pet.pojo.entity.User;
import com.dlnu.pet.pojo.dto.RegisterDTO;
import com.dlnu.pet.pojo.dto.LoginDTO;
import com.dlnu.pet.pojo.vo.Result;


public interface UserService extends IService<User> {
    /**
     * 发送验证码
     */
    Result sendCode(String phone, String type);
    
    /**
     * 用户注册
     */
    Result register(RegisterDTO dto);

    Result login(LoginDTO dto);

    /**
     * 退出登录
     * @param token 当前登录的token
     * @return 操作结果
     */
    Result logout(String token);
} 
