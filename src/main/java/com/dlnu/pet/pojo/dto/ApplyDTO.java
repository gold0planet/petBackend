package com.dlnu.pet.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("领养申请信息")
public class ApplyDTO {
    @ApiModelProperty("联系电话")
    private String phone;
    @ApiModelProperty("申请理由")
    private String reason;
    @ApiModelProperty("省份")
    private String province;
    @ApiModelProperty("城市")
    private String city;
}
