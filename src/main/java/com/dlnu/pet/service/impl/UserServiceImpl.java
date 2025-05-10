package com.dlnu.pet.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlnu.pet.mapper.UserMapper;
import com.dlnu.pet.pojo.dto.LoginDTO;
import com.dlnu.pet.pojo.dto.RegisterDTO;
import com.dlnu.pet.pojo.entity.User;
import com.dlnu.pet.pojo.vo.LoginVO;
import com.dlnu.pet.pojo.vo.Result;
import com.dlnu.pet.service.SmsService;
import com.dlnu.pet.service.UserService;
import com.dlnu.pet.util.JwtUtil;
import com.dlnu.pet.util.PasswordEncoder;
import com.dlnu.pet.util.PhoneUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @Autowired
    private SmsService smsService;
    
    //private static final String CODE_KEY_PREFIX = "code:";
    private static final long CODE_EXPIRE_TIME = 5; // 验证码有效期5分钟
    
    @Override
    public Result sendCode(String phone, String type) {
        // 1. 验证手机号格式
        if (!PhoneUtil.isValidPhone(phone)) {
            return Result.error(400, "手机号格式不正确");
        }
        
        // 2. 生成6位随机验证码
        String code = String.format("%06d", (int)(Math.random() * 1000000));
        
        // 3. 调用短信服务发送验证码
        try {
            smsService.sendCode(phone, code, type);
        } catch (Exception e) {
            log.error("发送验证码失败", e);
            return Result.error(500, "发送验证码失败");
        }
        
        // 4. 将验证码保存到Redis,5分钟有效期
        //String key = CODE_KEY_PREFIX + type + ":" + phone;
        String key = type + ":" + phone;

        redisTemplate.opsForValue().set(key, code, CODE_EXPIRE_TIME, TimeUnit.MINUTES);
        
        return Result.success(null);
    }
    
    @Override
    public Result register(RegisterDTO dto) {
        // 1. 验证验证码
        String key = "register:" + dto.getPhone();
        String code = redisTemplate.opsForValue().get(key);
        if (!dto.getCode().equals(code)) {
            return Result.error(400, "验证码错误");
        }
        
        // 2. 检查手机号是否已注册
        if (baseMapper.selectOne(new QueryWrapper<User>()
                .eq("phone", dto.getPhone())) != null) {
            return Result.error(400, "该手机号已注册");
        }
        
        // 3. 密码加密
        String encryptPwd = PasswordEncoder.encode(dto.getPassword());
        
        // 4. 保存用户信息
        User user = new User();
        user.setPhone(dto.getPhone());
        user.setPassword(encryptPwd);
        
        // 设置默认信息
        user.setUsername("momo");  // 默认用户名
        
        baseMapper.insert(user);
        
        // 5. 生成token
        //String token = JwtUtil.generateToken(user.getId());
        // token存入redis
        //redisTemplate.opsForValue().set(user.getId().toString(), token, 1, TimeUnit.HOURS);
        // 6. 删除验证码
        redisTemplate.delete(key);
        
        // 7. 构造返回的登录信息        
        return Result.success("注册成功");
    }

    @Override
    public Result login(LoginDTO dto) {
        // 1. 判断登录方式
        if ("password".equals(dto.getLoginType())) {
            // 密码登录
            User user = baseMapper.selectOne(new QueryWrapper<User>()
                    .eq("phone", dto.getPhone()));
                    
            if (user == null) {
                return Result.error(400, "用户不存在");
            }
            
            // 校验密码
            if (!PasswordEncoder.encode(dto.getPassword()).equals(user.getPassword())) {
                return Result.error(400, "密码错误");
            }
            
            // 生成token
            String token = JwtUtil.generateToken(user.getId());
            // 将token存入redis
            redisTemplate.opsForValue().set(user.getPhone(), token, 1, TimeUnit.HOURS);
            return Result.success(new LoginVO(
                token, 
                user.getId(), 
                user.getUsername(), 
                user.getAvatar()
            ));
            
        } else if ("code".equals(dto.getLoginType())) {
            // 验证码登录
            String key = "login:" + dto.getPhone();
            String code = redisTemplate.opsForValue().get(key);
            
            if (!dto.getCode().equals(code)) {
                return Result.error(400, "验证码错误");
            }
            
            // 验证码正确,查询用户是否存在
            User user = baseMapper.selectOne(new QueryWrapper<User>()
                    .eq("phone", dto.getPhone()));
                    
            if (user == null) {
                // 用户不存在则自动注册
                user = new User();
                user.setPhone(dto.getPhone());
                user.setPassword(PasswordEncoder.encode("123456"));
                user.setUsername("momo");
                baseMapper.insert(user);
            }
            
            // 删除验证码
            redisTemplate.delete(key);
            
            // 生成token
            String token = JwtUtil.generateToken(user.getId());
            // 将token存入redis
            redisTemplate.opsForValue().set(user.getPhone(), token, 1, TimeUnit.HOURS);
            return Result.success(new LoginVO(
                token, 
                user.getId(), 
                user.getUsername() != null ? user.getUsername() : "momo", 
                user.getAvatar()
            ));
            
        } else {
            return Result.error(400, "不支持的登录方式");
        }
    }

    @Override
    public Result logout(String token) {
        try {
            // 1. 解析token获取用户ID（可选）
            Long userId = JwtUtil.parseToken(token);
            // 2. 删除token
            redisTemplate.delete(userId.toString());
            
            return Result.success(null);
        } catch (Exception e) {
            log.error("退出登录失败", e);
            return Result.error(500, "退出登录失败");
        }
    }
} 