package com.dlnu.pet.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("流浪动物信息")
public class StrayPetDTO {    
    @ApiModelProperty("宠物名称")
    private String name;
    
    @ApiModelProperty("品种(cat-猫,dog-狗,other-其他)")
    private String breed;
    
    @ApiModelProperty("年龄")
    private Integer age;
    
    @ApiModelProperty("性别(male-公,female-母)")
    private String gender;
    
    @ApiModelProperty("状态(stray-流浪,adoption-待领养)")
    private String status;
    
    @ApiModelProperty("图片URL")
    private String image;
    
    @ApiModelProperty("省份")
    private String province;
    
    @ApiModelProperty("城市")
    private String city;
    
    @ApiModelProperty("描述信息")
    private String description;
    
    @ApiModelProperty("发现地点")
    private String foundLocation;
    
    @ApiModelProperty("发现日期")
    private String foundDate;
    
    @ApiModelProperty("健康状况")
    private String health;
    
    @ApiModelProperty("领养要求")
    private String requirements;
} 