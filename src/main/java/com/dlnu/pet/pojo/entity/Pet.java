package com.dlnu.pet.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("pet")
public class Pet {
    @TableId
    private Long id;
    private String name;
    private Integer breed;  // 0-猫 1-狗
    private Integer age;
    private Integer gender; // 0-母 1-公
    private Integer status = 1; // 默认状态1
    private String image;   // 图片路径
} 