package com.flowable.core.service;

import com.flowable.core.dto.SaveModelDto;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author: yuegc
 * @description:
 * @create: 2020/07/15 09:55
 */
public interface FlowModelService {

    /**
     * 新增流程模型
     * @param model
     */
    Model saveModel(SaveModelDto model) throws UnsupportedEncodingException;

    /**
     * 发布流程
     * @return
     */
    Deployment deploy(String modelId);

    /**
     * 流程模型列表
     * @return
     */
    List<Model> modelList();
}
