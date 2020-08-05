package com.flowable.core;

import com.flowable.core.dto.SaveModelDto;
import com.flowable.core.service.FlowModelService;
import liquibase.pro.packaged.A;
import org.flowable.common.engine.api.repository.EngineResource;
import org.flowable.engine.*;
import org.flowable.engine.FormService;
import org.flowable.engine.form.FormProperty;
import org.flowable.engine.form.StartFormData;
import org.flowable.engine.form.TaskFormData;
import org.flowable.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.form.api.*;
import org.flowable.form.model.FormField;
import org.flowable.form.model.SimpleFormModel;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@ConditionalOnClass
class FlowableApplicationTests {
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private FormRepositoryService formRepositoryService;
	@Autowired
	private FormService formService;
	@Autowired
	private org.flowable.form.api.FormService formServices;
	@Autowired
	private FlowModelService flowModelService;

	@Test
	void saveModel(){
		SaveModelDto saveModelDTO = new SaveModelDto();
		saveModelDTO.setId("6e243355-c73d-11ea-858f-acde48001122");
		saveModelDTO.setName("测试BPMN模型");
		saveModelDTO.setKey("test_bpmn");
		saveModelDTO.setModelXml(bpmxml);
		Model model = flowModelService.saveModel(saveModelDTO);
		System.out.println(model.getId());
	}

	@Test
	void deployModel() {
		Deployment deploy = flowModelService.deploy("6e243355-c73d-11ea-858f-acde48001122");
		System.out.println(deploy.getId());
		Map<String, EngineResource> resources = deploy.getResources();
		System.out.println(resources);
	}

	@Test
	void deploy() {
		Deployment deployment = repositoryService.createDeployment()
				.name("表单流程")
				.key("formProcess")
				.addString("flowable/表单流程.bpmn20.xml", bpmxml)
				//.addClasspathResource("flowable/test-form.bpmn20.xml")
				.deploy();

		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				//.processDefinitionKey("formProcess")
				.deploymentId(deployment.getId())
				.latestVersion()
				.singleResult();
		String processDefinitionId = processDefinition.getId();
		System.out.println(processDefinitionId);

		FormDeployment formDeployment = formRepositoryService.createDeployment()
				.name("form1")
				.addString("flowable/form1.form", formJson)
				.parentDeploymentId(deployment.getId())
				.deploy();
		FormDefinition formDefinition = formRepositoryService.createFormDefinitionQuery().deploymentId(formDeployment.getId()).singleResult();
		String formDefinitionId = formDefinition.getId();

		//启动实例并且设置表单的值
		String outcome = "测试";
		Map<String, String> formProperties;
		formProperties = new HashMap<>();
		formProperties.put("startUser", "ygc");
		formProperties.put("days", "3");
		String processInstanceName = "shareniu";
		ProcessInstance processInstance = formService.submitStartFormData(processDefinitionId, formProperties);

		String processInstanceId = processInstance.getProcessInstanceId();


		//查询表单信息
		StartFormData startFormData = formService.getStartFormData(processDefinitionId);
		List<FormProperty> formProperties1 = startFormData.getFormProperties();
		//System.out.println(fm.getId());
		System.out.println(startFormData.getFormKey());
		for (FormProperty ff : formProperties1) {
			System.out.println("######################");
			System.out.println(ff.getId());
			System.out.println(ff.getName());
			System.out.println(ff.getType());
			System.out.println(ff.getValue());
			System.out.println("######################");

		}


		//查询个人任务并填写表单
		Map<String, String> formProperties2 = new HashMap<>();
		formProperties2.put("startUser", "ygc");
		formProperties2.put("days", "3");
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		String taskId = task.getId();
		String outcome2="测试";
		formService.submitTaskFormData(taskId, formProperties2);

		//获取个人任务表单
		TaskFormData form = formService.getTaskFormData(taskId);
		List<FormProperty> formProperties3 = form.getFormProperties();
		formProperties3.forEach(formProperty -> {
			System.out.println("######################");
			System.out.println(formProperty.getId());
			System.out.println(formProperty.getName());
			System.out.println(formProperty.getType());
			System.out.println(formProperty.getValue());
			System.out.println("######################");
		});
	}

	@Test
	void task() {
		FormInfo taskFM = taskService.getTaskFormModel("13c4bff1-d551-11ea-9725-acde48001122");
		SimpleFormModel taskFMFormModel = (SimpleFormModel) taskFM.getFormModel();
		List<FormField> fields = taskFMFormModel.getFields();
		fields.forEach(field -> {
			System.out.println("######################");
			System.out.println(field.getId());
			System.out.println(field.getName());
			System.out.println(field.getType());
			System.out.println(field.getValue());
			System.out.println(field.getPlaceholder());
			System.out.println("######################");
		});
	}

	@Test
	void taskForm() {
		//获取个人任务表单
		TaskFormData form = formService.getTaskFormData("13c4bff1-d551-11ea-9725-acde48001122");
		List<FormProperty> formProperties1 = form.getFormProperties();
		formProperties1.forEach(formProperty -> {
			System.out.println("######################");
			System.out.println(formProperty.getId());
			System.out.println(formProperty.getName());
			System.out.println(formProperty.getType());
			System.out.println(formProperty.getValue());
			System.out.println("######################");
		});
	}

	@Test
	void startForm() {
		StartFormData startFormData = formService.getStartFormData("holidayRequest:11:13880604-d551-11ea-9725-acde48001122");
		FormInfo formModelByKey = formRepositoryService.getFormModelByKey(startFormData.getFormKey());
		SimpleFormModel formModel = (SimpleFormModel) formModelByKey.getFormModel();
		List<FormField> fields = formModel.getFields();
		fields.forEach(formProperty -> {
			System.out.println("****######################");
			System.out.println(formProperty.getId());
			System.out.println(formProperty.getValue());
			System.out.println(formProperty.getName());
			System.out.println(formProperty.getType());
			System.out.println(formProperty.getValue());
			System.out.println("****######################");
		});
	}


	private static String bpmxml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
			"<definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"\n" +
			"             xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
			"             xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n" +
			"             xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\"\n" +
			"             xmlns:omgdc=\"http://www.omg.org/spec/DD/20100524/DC\"\n" +
			"             xmlns:omgdi=\"http://www.omg.org/spec/DD/20100524/DI\"\n" +
			"             xmlns:flowable=\"http://flowable.org/bpmn\"\n" +
			"             typeLanguage=\"http://www.w3.org/2001/XMLSchema\"\n" +
			"             expressionLanguage=\"http://www.w3.org/1999/XPath\"\n" +
			"             targetNamespace=\"http://www.flowable.org/processdef\">\n" +
			"\n" +
			"    <process id=\"holidayRequest\" name=\"Holiday Request\" isExecutable=\"true\">\n" +
			"\n" +
			"        <startEvent id=\"startEvent\" flowable:formKey=\"form1\"/>\n" +
			"        <sequenceFlow sourceRef=\"startEvent\" targetRef=\"approveTask\"/>\n" +
			"\n" +
			"        <userTask id=\"approveTask\" name=\"Approve or reject request\" flowable:formKey=\"form1\" flowable:candidateGroups=\"managers\"/>\n" +
			"        <sequenceFlow sourceRef=\"approveTask\" targetRef=\"holidayApprovedTask\"/>\n" +
			"\n" +
			"        <userTask id=\"holidayApprovedTask\" name=\"Holiday approved\" flowable:formKey=\"form1\" flowable:assignee=\"employee\"/>\n" +
			"        <sequenceFlow sourceRef=\"holidayApprovedTask\" targetRef=\"approveEnd\"/>\n" +
			"\n" +
			"        <endEvent id=\"approveEnd\"/>\n" +
			"\n" +
			"    </process>\n" +
			"\n" +
			"</definitions>";
	private static String formJson = "{\n" +
			"\"key\": \"form1\",\n" +
			"\"name\": \"请假流程\",\n" +
			"\"fields\": [\n" +
			"            {\n" +
			"            \"id\": \"startUser\",\n" +
			"            \"name\": \"申请人\",\n" +
			"            \"type\": \"text\",\n" +
			"            \"required\": \"true\",\n" +
			"            \"placeholder\": \"empty\"\n" +
			"\n" +
			"            },\n" +
			"            {\n" +
			"            \"id\": \"days\",\n" +
			"            \"name\": \"请假时长\",\n" +
			"            \"type\": \"text\",\n" +
			"            \"required\": \"true\",\n" +
			"            \"placeholder\": \"empty\"\n" +
			"            }\n" +
			"    ]\n" +
			"}";
}
