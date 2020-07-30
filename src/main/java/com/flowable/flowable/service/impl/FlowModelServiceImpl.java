package com.flowable.flowable.service.impl;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.flowable.flowable.dto.SaveModelDto;
import com.flowable.flowable.service.FlowModelService;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.util.io.InputStreamSource;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: yuegc
 * @description:
 * @create: 2020/07/15 09:56
 */
@Service
public class FlowModelServiceImpl implements FlowModelService {
    @Autowired
    private RepositoryService repositoryService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Model saveModel(SaveModelDto saveModelDTO) {
        Model model;
        if (StrUtil.isEmpty(saveModelDTO.getId())) {
            model = repositoryService.newModel();
        } else {
            model = repositoryService.getModel(saveModelDTO.getId());
        }
        model.setName(saveModelDTO.getName());
        model.setKey(saveModelDTO.getKey());
        model.setCategory(saveModelDTO.getCategory());
        //创建model需要的metaInfo属性数据
        ObjectNode metaInfo = new ObjectMapper().createObjectNode();
        metaInfo.put("name", model.getName());
        metaInfo.put("key", model.getKey());
        metaInfo.put("description", "");
        model.setMetaInfo(metaInfo.toString());
        //保存模型
        repositoryService.saveModel(model);
        //保存xml
        repositoryService.addModelEditorSource(model.getId(), saveModelDTO.getModelXml().getBytes(StandardCharsets.UTF_8));
        //保存流程图片
        ByteArrayInputStream inputStream = new ByteArrayInputStream((saveModelDTO.getModelXml().getBytes(StandardCharsets.UTF_8)));
        InputStreamSource inputStreamSource = new InputStreamSource(inputStream);
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(inputStreamSource, true, false, "UTF-8");
        List<String> highLightedActivities = new ArrayList<>();
        DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
        InputStream is = defaultProcessDiagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivities, true);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            byte[] buff = new byte[1024];
            while ((is.read(buff)) != -1) {
                outputStream.write(buff);
            }
            byte[] result = outputStream.toByteArray();
            // 添加图片源
            repositoryService.addModelEditorSourceExtra(model.getId(), result);
        } catch (IOException e) {
            //message = "流程图创建失败！";
            e.printStackTrace();
        }
        return model;
    }

    @Override
    public Deployment deploy(String modelId) {
        Model model = repositoryService.getModel(modelId);
        //获取模型
        byte[] bytes = repositoryService.getModelEditorSource(modelId);
        if (bytes == null) {
            //模型数据为空，请先设计流程并成功保存，再进行发布
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        InputStreamSource inputStreamSource = new InputStreamSource(inputStream);
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(inputStreamSource, true, false, "UTF-8");
        if (bpmnModel.getProcesses().size() == 0) {
            //数据模型不符要求，请至少设计一条主线流程
        }
        //发布流程
        Deployment deploy = repositoryService.createDeployment()
                .name(model.getName())
                .key(model.getKey())
                .addBpmnModel(model.getName() + ".bpmn20.xml", bpmnModel)
                .deploy();
        model.setDeploymentId(deploy.getId());
        repositoryService.saveModel(model);
        return deploy;
    }

    @Override
    public List<Model> modelList() {
        List<Model> models = repositoryService.createModelQuery().deployed().list();
        return models;
    }
}
