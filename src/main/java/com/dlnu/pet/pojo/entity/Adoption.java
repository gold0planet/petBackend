package com.dlnu.pet.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("adoption")
public class Adoption {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    @TableField("pet_id")
    private Long petId;
    
    @TableField("user_id")
    private Long userId;
    
    private String health;    // 健康状况
    private String province;  // 省份
    private String city;      // 城市
    private String description; // 宠物描述
    private String requirements; // 领养要求
    private Integer status = 1;  // 默认状态1（发布中）
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
} 