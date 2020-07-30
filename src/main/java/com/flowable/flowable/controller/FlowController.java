package com.flowable.flowable.controller;

import org.flowable.engine.*;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: yuegc
 * @description:
 * @create: 2020/03/19 10:10
 */
@RestController
@RequestMapping("/flow")
public class FlowController {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Resource
    private ProcessEngine processEngine;
    @Autowired
    private HistoryService historyService;

    @GetMapping(value = "add")
    public String addExpense(String userId, Integer day, String deployId) {
        // 启动流程
        HashMap<String, Object> map = new HashMap<>();
        map.put("startUser", userId);
        map.put("day", day);
        map.put("skip", true);
        map.put("_FLOWABLE_SKIP_EXPRESSION_ENABLED", true);
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployId)
                .latestVersion()
                .singleResult();
        ProcessInstance processInstance = runtimeService.startProcessInstanceWithForm(processDefinition.getId(), "测试", map, "请假流程");
        //ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("processKey", map);
        return "提交成功.流程Id为：" + processInstance.getId();
    }
    /**
     * 获取审批管理列表
     * 获取出此用户需要处理的流程
     */
    @GetMapping(value = "/list")
    public Object list(String userId) {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().list();
        for (Task task : tasks) {
            System.out.println(task.toString());
        }
        return tasks.size();
    }
    /**
     * 批准
     * 通过前端传入的任务ID来对此流程进行同意处理
     * @param taskId 任务ID
     */
    @GetMapping(value = "apply")
    public String apply(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new RuntimeException("流程不存在");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        taskService.complete(taskId, map);
        return "processed ok!";
    }
    /**
     * 拒绝
     */
    @GetMapping(value = "reject")
    public String reject(String taskId) {
        HashMap<String, Object> map = new HashMap<>();
        taskService.complete(taskId, map);
        return "reject";
    }
}
