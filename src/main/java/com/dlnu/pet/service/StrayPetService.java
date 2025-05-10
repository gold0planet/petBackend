package com.dlnu.pet.service;

import java.util.Map;

import com.dlnu.pet.pojo.dto.ApplyDTO;
import com.dlnu.pet.pojo.dto.StrayPetDTO;

public interface StrayPetService {
    
    /**
     * 获取流浪动物列表
     */
    Map<String, Object> getStrayPetList(Integer page, Integer pageSize, String breed, 
            Integer age, String gender, String status, String province, String city);
    
    /**
     * 获取流浪动物详情
     */
    StrayPetDTO getStrayPetDetail(Long id);
    
    /**
     * 为流浪动物发布领养信息
     */
    Long publishAdoption(StrayPetDTO adoptionInfo);
    
    /**
     * 申请领养流浪动物
     */
    Long applyAdoption(Long id, ApplyDTO applicationInfo);
} 