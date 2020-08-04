package com.flowable.core.service;

import com.flowable.core.dto.SaveFormDto;
import com.flowable.core.entity.FormModel;
import org.flowable.engine.form.FormProperty;
import org.flowable.form.api.FormDeployment;
import org.flowable.form.model.FormField;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author: yuegc
 * @description:
 * @create: 2020/07/15 09:55
 */
public interface FlowFormService {
    void saveForm(SaveFormDto saveFormDto);

    FormDeployment deploy(String formId);

    Page<FormModel> formList();

    List<FormField> getStartForm(String processDefinitionId);

    List<FormProperty> getTaskForm(String taskId);
}
