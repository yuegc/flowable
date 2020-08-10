package com.flowable.core.controller;

import com.flowable.core.dto.SaveModelDto;
import com.flowable.core.service.FlowModelService;
import com.flowable.core.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.flowable.ui.modeler.domain.AbstractModel;
import org.flowable.ui.modeler.domain.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: yuegc
 * @description:
 * @create: 2020/07/15 09:53
 */
@Api(tags = "流程模型管理")
@RestController
@RequestMapping("/model")
public class ModelController {
    @Autowired
    FlowModelService modelService;

    @ApiOperation(value = "保存流程模型")
    @PostMapping("/save_model")
    public Result<Model> saveModel(@RequestBody SaveModelDto saveModelDto) {
        Model model = modelService.saveModel(saveModelDto);
        return Result.success(model);
    }

    @ApiOperation(value = "发布流程")
    @ApiImplicitParam(name = "modelId", value = "流程模型id", required = true)
    @GetMapping("/deploy")
    public Result<Object> deploy(@RequestParam String modelId) {
        modelService.deploy(modelId);
        return Result.success();
    }

    @ApiOperation(value = "流程模型列表")
    @GetMapping("/model_list")
    public Result<Object> modelList() {
        List<AbstractModel> models = modelService.modelList();
        return Result.success(models);
    }
}
