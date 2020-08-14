package com.flowable.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.flowable.core.dto.SaveModelDto;
import com.flowable.core.service.FlowModelService;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.BpmnAutoLayout;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.Process;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.editor.language.json.converter.util.CollectionUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.idm.api.User;
import org.flowable.ui.common.security.SecurityUtils;
import org.flowable.ui.common.service.exception.BadRequestException;
import org.flowable.ui.common.util.XmlUtil;
import org.flowable.ui.modeler.domain.AbstractModel;
import org.flowable.ui.modeler.domain.Model;
import org.flowable.ui.modeler.repository.ModelRepository;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.flowable.validation.ProcessValidator;
import org.flowable.validation.ProcessValidatorFactory;
import org.flowable.validation.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.util.Calendar;
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
    @Autowired
    private ModelRepository modelRepository;
    @Autowired
    private ModelService modelService;

    private BpmnXMLConverter bpmnXmlConverter = new BpmnXMLConverter();
    private BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Model saveModel(SaveModelDto saveModelDTO) {
        InputStream inputStream = new ByteArrayInputStream(saveModelDTO.getModelXml().getBytes());
        Model newModel = new Model();
        try {
            XMLInputFactory xif = XmlUtil.createSafeXmlInputFactory();
            InputStreamReader xmlIn = new InputStreamReader(inputStream, "UTF-8");
            XMLStreamReader xtr = xif.createXMLStreamReader(xmlIn);
            BpmnModel bpmnModel = bpmnXmlConverter.convertToBpmnModel(xtr);
            //模板验证
            ProcessValidator validator = new ProcessValidatorFactory().createDefaultProcessValidator();
            List<ValidationError> errors = validator.validate(bpmnModel);
            if (CollectionUtils.isNotEmpty(errors)) {
                StringBuffer es = new StringBuffer();
                errors.forEach(ve -> es.append(ve.toString()).append("/n"));
                //return new ReturnVo(ReturnCode.SUCCESS,"模板验证失败，原因: " + es.toString());
            }
            String fileName = bpmnModel.getMainProcess().getName();
            if (CollectionUtils.isEmpty(bpmnModel.getProcesses())) {
                //return new ReturnVo(ReturnCode.SUCCESS,"No process found in definition " + fileName);
            }
            if (bpmnModel.getLocationMap().size() == 0) {
                BpmnAutoLayout bpmnLayout = new BpmnAutoLayout(bpmnModel);
                bpmnLayout.execute();
            }
            ObjectNode modelNode = bpmnJsonConverter.convertToJson(bpmnModel);
            Process process = bpmnModel.getMainProcess();
            String name = process.getId();
            if (StringUtils.isNotEmpty(process.getName())) {
                name = process.getName();
            }
            String description = process.getDocumentation();
            User createdBy = SecurityUtils.getCurrentUserObject();
            //查询是否已经存在流程模板
            List<Model> models = modelRepository.findByKeyAndType(process.getId(), AbstractModel.MODEL_TYPE_BPMN);
            if (CollectionUtils.isNotEmpty(models)) {
                Model updateModel = models.get(0);
                newModel.setId(updateModel.getId());
            }
            newModel.setName(name);
            newModel.setKey(process.getId());
            newModel.setModelType(AbstractModel.MODEL_TYPE_BPMN);
            newModel.setCreated(Calendar.getInstance().getTime());
            //newModel.setCreatedBy(createdBy.getId());
            newModel.setDescription(description);
            newModel.setModelEditorJson(modelNode.toString());
            newModel.setLastUpdated(Calendar.getInstance().getTime());
            //newModel.setLastUpdatedBy(createdBy.getId());
            modelService.saveModel(newModel);
            //return returnVo;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            //LOGGER.error("Import failed for {}", e);
            //returnVo =  new ReturnVo(ReturnCode.SUCCESS,"Import failed for , error message " + e.getMessage());
        }
        return newModel;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Deployment deploy(String modelId) {
        Model model = modelService.getModel(modelId);
        //获取模型
        BpmnModel bpmnModel = modelService.getBpmnModel(model);
        //必须指定文件后缀名否则部署不成功
        return repositoryService.createDeployment()
                .name(model.getName())
                .key(model.getKey())
                .category(null)
                .tenantId(null)
                .addBpmnModel(model.getKey() + ".bpmn", bpmnModel)
                .deploy();
    }

    @Override
    public List<AbstractModel> modelList() {
        //modelService.getm
        //return models;
        return modelService.getModelsByModelType(AbstractModel.MODEL_TYPE_BPMN);
    }
}
