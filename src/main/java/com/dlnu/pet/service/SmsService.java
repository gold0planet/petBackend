package com.dlnu.pet.service;

public interface SmsService {
    /**
     * 发送验证码
     * @param phone 手机号
     * @param code 验证码
     * @param type 类型(register-注册,login-登录)
     * @throws Exception 
     */
    void sendCode(String phone, String code, String type) throws Exception;
} 