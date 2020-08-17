package com.flowable.core.service.impl;

import com.flowable.core.dto.SaveFormDto;
import com.flowable.core.service.FlowFormService;
import org.flowable.editor.language.json.converter.util.CollectionUtils;
import org.flowable.engine.FormService;
import org.flowable.engine.form.FormProperty;
import org.flowable.engine.form.TaskFormData;
import org.flowable.form.api.FormDeployment;
import org.flowable.form.api.FormInfo;
import org.flowable.form.api.FormRepositoryService;
import org.flowable.form.model.FormField;
import org.flowable.form.model.SimpleFormModel;
import org.flowable.ui.modeler.domain.AbstractModel;
import org.flowable.ui.modeler.domain.Model;
import org.flowable.ui.modeler.repository.ModelRepository;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
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
    private ModelRepository modelRepository;
    @Autowired
    private ModelService modelService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveForm(SaveFormDto saveFormDto) {
        Model newModel = new Model();
        //查询是否已经存在流程模板
        List<Model> models = modelRepository.findByKeyAndType(saveFormDto.getKey(), AbstractModel.MODEL_TYPE_FORM);
        if (CollectionUtils.isNotEmpty(models)) {
            Model updateModel = models.get(0);
            newModel.setId(updateModel.getId());
        }
        newModel.setName(saveFormDto.getName());
        newModel.setKey(saveFormDto.getKey());
        newModel.setModelType(AbstractModel.MODEL_TYPE_BPMN);
        newModel.setCreated(Calendar.getInstance().getTime());
        //newModel.setCreatedBy(createdBy.getId());
        newModel.setDescription(saveFormDto.getDescription());
        newModel.setModelEditorJson(saveFormDto.getFormJson());
        newModel.setLastUpdated(Calendar.getInstance().getTime());
        //newModel.setLastUpdatedBy(createdBy.getId());
        modelService.saveModel(newModel);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public FormDeployment deploy(String formId) {
        Model model = modelService.getModel(formId);
        if (model == null) {
            //表单数据为空，请先设计表单并成功保存，再进行发布
        }
        FormDeployment deployment = formRepositoryService.createDeployment()
                .name(model.getName())
                .addString(model.getName() + ".form", model.getModelEditorJson())
                .deploy();
        return deployment;
    }

    @Override
    public List<AbstractModel> formList() {
        return modelService.getModelsByModelType(AbstractModel.MODEL_TYPE_FORM);
    }

    @Override
    public List<FormField> getStartForm(String processDefinitionId) {
        String startFormKey = formService.getStartFormKey(processDefinitionId);
        FormInfo formInfo = formRepositoryService.getFormModelByKey(startFormKey);
        SimpleFormModel simpleFormModel = (SimpleFormModel) formInfo.getFormModel();
        return simpleFormModel.getFields();
    }

    @Override
    public List<FormProperty> getTaskForm(String taskId) {
        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        return taskFormData.getFormProperties();
    }
}
