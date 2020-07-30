package com.flowable.core.service;

import com.flowable.core.dto.SaveFormDto;
import com.flowable.core.entity.FormModel;
import org.flowable.form.api.FormDeployment;

import java.util.List;

/**
 * @author: yuegc
 * @description:
 * @create: 2020/07/15 09:55
 */
public interface FlowFormService {
    void saveForm(SaveFormDto saveFormDto);

    FormDeployment deployForm(String formId);

    List<FormModel> formModelList();
}
