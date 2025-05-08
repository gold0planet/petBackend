package com.dlnu.pet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlnu.pet.mapper.AdoptionMapper;
import com.dlnu.pet.mapper.PetMapper;
import com.dlnu.pet.pojo.dto.ApplyDTO;
import com.dlnu.pet.pojo.dto.StrayPetDTO;
import com.dlnu.pet.pojo.entity.Adoption;
import com.dlnu.pet.pojo.entity.Pet;
import com.dlnu.pet.service.StrayPetService;
import com.dlnu.pet.util.PetEnumUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StrayPetServiceImpl implements StrayPetService {

    @Autowired
    private PetMapper petMapper;
    
    @Autowired
    private AdoptionMapper adoptionMapper;

    @Override
    public Map<String, Object> getStrayPetList(Integer page, Integer pageSize, String breed,
            Integer age, String gender, String status, String province, String city) {
        // 创建分页对象
        Page<Pet> pageParam = new Page<>(page, pageSize);
        
        // 创建查询条件
        LambdaQueryWrapper<Pet> queryWrapper = new LambdaQueryWrapper<>();
        
        // 设置状态为流浪或待领养
        queryWrapper.eq(Pet::getStatus, PetEnumUtil.PetStatus.STRAY.getCode());
        
        // 添加查询条件
        if (StringUtils.hasText(breed)) {
            queryWrapper.eq(Pet::getBreed, PetEnumUtil.Breed.getCodeByDesc(breed));
        }
        if (age != null) {
            queryWrapper.eq(Pet::getAge, age);
        }
        if (StringUtils.hasText(gender)) {
            queryWrapper.eq(Pet::getGender, PetEnumUtil.Gender.getCodeByDesc(gender));
        }
        
        // 执行分页查询
        Page<Pet> result = petMapper.selectPage(pageParam, queryWrapper);
        List<Pet> records = result.getRecords();
        
        // 由于分页可能存在问题，手动计算总数
        long total = result.getTotal();
        if (total == 0 && records != null && !records.isEmpty()) {
            // 如果total为0但records不为空，手动查询总数
            total = petMapper.selectCount(queryWrapper);
        }
        
        // 转换数字为文字
        List<Map<String, Object>> petList = new ArrayList<>();
        if (records != null && !records.isEmpty()) {
            for (Pet pet : records) {
                Map<String, Object> petMap = new HashMap<>();
                petMap.put("id", pet.getId());
                petMap.put("name", pet.getName());
                petMap.put("breed", PetEnumUtil.Breed.getDescByCode(pet.getBreed()));
                petMap.put("age", pet.getAge());
                petMap.put("gender", PetEnumUtil.Gender.getDescByCode(pet.getGender()));
                petMap.put("status", PetEnumUtil.PetStatus.getDescByCode(pet.getStatus()));
                petMap.put("image", pet.getImage());
                petList.add(petMap);
            }
        }
        
        // 转换为DTO
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("list", petList);
        return map;
    }

    @Override
    public StrayPetDTO getStrayPetDetail(Long id) {
        // 查询宠物信息
        Pet pet = petMapper.selectById(id);
        if (pet == null || !(pet.getStatus() == PetEnumUtil.PetStatus.STRAY.getCode())) {
            return null;
        }
        
        // 转换为DTO
        StrayPetDTO dto = new StrayPetDTO();
        BeanUtils.copyProperties(pet, dto);
        
        // 转换数字为文字
        dto.setBreed(PetEnumUtil.Breed.getDescByCode(pet.getBreed()));
        dto.setGender(PetEnumUtil.Gender.getDescByCode(pet.getGender()));
        dto.setStatus(PetEnumUtil.PetStatus.getDescByCode(pet.getStatus()));
        
        return dto;
    }

    @Override
    public Long publishAdoption(StrayPetDTO adoptionInfo) {
        // 创建宠物信息
        Pet pet = new Pet();
        BeanUtils.copyProperties(adoptionInfo, pet);
        pet.setStatus(PetEnumUtil.PetStatus.STRAY.getCode());
        petMapper.insert(pet);
        
        // 创建领养信息记录
        Adoption adoption = new Adoption();
        adoption.setUserId(1L); // TODO: 从当前登录用户获取userId
        adoption.setHealth(adoptionInfo.getHealth());
        adoption.setProvince(adoptionInfo.getProvince());
        adoption.setCity(adoptionInfo.getCity());
        adoption.setDescription(adoptionInfo.getDescription());
        adoption.setRequirements(adoptionInfo.getRequirements());
        adoption.setStatus(PetEnumUtil.AdoptionStatus.PUBLISH.getCode());
        
        // 保存领养信息
        adoptionMapper.insert(adoption);
        
        return adoption.getId();
    }

    @Override
    public Long applyAdoption(Long id, ApplyDTO applicationInfo) {
        // 查询宠物信息
        Pet pet = petMapper.selectById(id);
        if (pet == null) {
            throw new RuntimeException("宠物不存在");
        }        
        // 创建领养申请记录
        Adoption adoption = new Adoption();
        adoption.setPetId(id);
        adoption.setUserId(1L); // TODO: 从当前登录用户获取userId
        adoption.setProvince(applicationInfo.getProvince());
        adoption.setCity(applicationInfo.getCity());
        adoption.setRequirements(applicationInfo.getReason()+"\n"+applicationInfo.getPhone());
        adoption.setStatus(PetEnumUtil.AdoptionStatus.WAITING.getCode());
        adoptionMapper.insert(adoption);

        return adoptionMapper.selectLastInsertId();
    }
} 