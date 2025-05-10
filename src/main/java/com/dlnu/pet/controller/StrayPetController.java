package com.dlnu.pet.controller;

import com.dlnu.pet.pojo.vo.Result;
import com.dlnu.pet.pojo.dto.ApplyDTO;
import com.dlnu.pet.pojo.dto.StrayPetDTO;
import com.dlnu.pet.service.StrayPetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = "流浪动物管理接口")
@RestController
@RequestMapping("/api/pets/stray")
public class StrayPetController {

    @Autowired
    private StrayPetService strayPetService;

    @ApiOperation("获取流浪动物列表")
    @GetMapping
    public Result getStrayPetList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer pageSize,
            @RequestParam(required = false) String breed,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String province,
            @RequestParam(required = false) String city) {
        return Result.success(strayPetService.getStrayPetList(page, pageSize, breed, age, gender, status, province, city));
    }

    @ApiOperation("获取流浪动物详情")
    @GetMapping("/{id}")
    public Result getStrayPetDetail(@PathVariable Long id) {
        return Result.success(strayPetService.getStrayPetDetail(id));
    }

    @ApiOperation("为流浪动物发布领养信息")
    @PostMapping
    public Result publishAdoption(
            @RequestBody StrayPetDTO adoptionInfo) {
        return Result.success(strayPetService.publishAdoption(adoptionInfo));
    }

    @ApiOperation("申请领养流浪动物")
    @PostMapping("/{id}/apply")
    public Result applyAdoption(
            @PathVariable Long id,
            @RequestBody ApplyDTO applicationInfo) {
        return Result.success(strayPetService.applyAdoption(id, applicationInfo));
    }
} 