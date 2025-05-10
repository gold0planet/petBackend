package com.dlnu.pet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dlnu.pet.pojo.entity.Pet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PetMapper extends BaseMapper<Pet> {
} 