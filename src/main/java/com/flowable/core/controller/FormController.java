
package com.flowable.core.controller;

import com.flowable.core.dto.SaveFormDto;
import com.flowable.core.service.FlowFormService;
import com.flowable.core.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.flowable.form.api.FormDeployment;
import org.flowable.ui.modeler.domain.AbstractModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author: yuegc
 * @description:
 * @create: 2020/07/15 09:53
 */

@Api(tags = "表单管理")
@RestController
@RequestMapping("/form")
public class FormController {
    @Autowired
    private FlowFormService flowFormService;

    @ApiOperation(value = "保存表单模型")
    @PostMapping("/save_form")
    public Result<Object> saveForm(@RequestBody SaveFormDto saveFormDto) {
        flowFormService.saveForm(saveFormDto);
        return Result.success();
    }

    @ApiOperation(value = "发布表单")
    @GetMapping("/deploy")
    public Result<Object> deploy(@RequestParam String formId) {
        FormDeployment deploy = flowFormService.deploy(formId);
        return Result.success(deploy);
    }

    @ApiOperation("表单模型列表")
    @GetMapping("/form_list")
    public Result<List<AbstractModel>> formList() {
        List<AbstractModel> formList = flowFormService.formList();
        return Result.success(formList);
    }
}
