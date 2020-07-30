package com.flowable.flowable.service;

import com.flowable.flowable.dto.SaveFormDto;
import org.flowable.form.api.FormDeployment;

/**
 * @author: yuegc
 * @description:
 * @create: 2020/07/15 09:55
 */
public interface FlowFormService {
    void saveForm(SaveFormDto saveFormDto);

    FormDeployment deployForm(String formId);
}
