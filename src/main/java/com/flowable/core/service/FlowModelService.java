package com.flowable.core.service;

import com.flowable.core.dto.SaveModelDto;
import org.flowable.engine.repository.Deployment;
import org.flowable.ui.modeler.domain.AbstractModel;
import org.flowable.ui.modeler.domain.Model;

import java.util.List;

/**
 * @author: yuegc
 * @description:
 * @create: 2020/07/15 09:55
 */
public interface FlowModelService {

    /**
     * 新增流程模型
     * @param saveModelDto
     */
    Model saveModel(SaveModelDto saveModelDto);

    /**
     * 发布流程
     * @return
     */
    Deployment deploy(String modelId);

    /**
     * 流程模型列表
     * @return
     */
    List<AbstractModel> modelList();
}
