package com.flowable.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: yuegc
 * @description:
 * @create: 2020/07/15 14:31
 */
@Data
@ApiModel("保存流程模型入参")
public class SaveModelDto {
    @ApiModelProperty("id,新增时必传")
    String id;
    @ApiModelProperty(value = "流程名称", required = true)
    String name;
    @ApiModelProperty(value = "流程key", required = true)
    String key;
    @ApiModelProperty("流程分类")
    String category;
    @ApiModelProperty(value = "流程xml", required = true)
    String modelXml;
}
