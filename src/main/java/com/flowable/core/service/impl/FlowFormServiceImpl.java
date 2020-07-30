package com.flowable.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.flowable.core.dto.SaveFormDto;
import com.flowable.core.entity.FormModel;
import com.flowable.core.repository.FormModelDao;
import com.flowable.core.service.FlowFormService;
import org.flowable.form.api.FormDeployment;
import org.flowable.form.api.FormRepositoryService;
import org.flowable.form.api.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: yuegc
 * @description:
 * @create: 2020/07/15 09:56
 */
@Service
public class FlowFormServiceImpl implements FlowFormService {
    @Autowired
    private FormRepositoryService formRepositoryService;
    @Autowired
    private FormService formService;
    @Autowired
    private org.flowable.engine.FormService formServices;
    @Autowired
    private FormModelDao formModelDao;

    @Override
    public void saveForm(SaveFormDto saveFormDto) {
        FormModel formModel;
        if (StrUtil.isBlank(saveFormDto.getId())) {
            formModel = new FormModel();
            saveFormDto.setId(UUID.randomUUID().toString());
        } else {
            formModel = formModelDao.getFormModelById(saveFormDto.getId());
        }
        BeanUtil.copyProperties(saveFormDto, formModel);
        formModelDao.save(formModel);
    }

    @Override
    public FormDeployment deployForm(String formId) {
        FormModel formModel = formModelDao.getFormModelById(formId);
        if (formModel == null) {
            //表单数据为空，请先设计表单并成功保存，再进行发布
        }
        FormDeployment deployment = formRepositoryService.createDeployment()
                .name(formModel.getName())
                .addString(formModel.getName() + ".form", formModel.getFormJson())
                .deploy();
        formModel.setDeploymentId(deployment.getId());
        formModelDao.save(formModel);
        return deployment;
    }

    @Override
    public List<FormModel> formModelList() {
        Pageable pageable = PageRequest.of(1, 10, Sort.Direction.DESC, "createTime");
        Page<FormModel> formModels = formModelDao.findAll(pageable);
        return formModelDao.findAll(pageable).toList();
    }
}
