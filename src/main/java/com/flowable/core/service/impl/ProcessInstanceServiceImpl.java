package com.flowable.core.service.impl;

import com.flowable.core.service.ProcessInstanceService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: yuegc
 * @description:
 * @create: 2020/07/15 10:49
 */
@Service
public class ProcessInstanceServiceImpl implements ProcessInstanceService {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void instanceList() {
        runtimeService.createProcessInstanceQuery().list();
    }

    @Override
    public void startInstance() {

    }

    @Override
    public void deleteInstance() {
        runtimeService.deleteProcessInstance(null, null);
    }

    @Override
    public void suspendInstance() {
        runtimeService.suspendProcessInstanceById(null);
    }

    @Override
    public void activeInstance() {
        runtimeService.activateProcessInstanceById(null);
    }


    private ProcessInstance getProcessInstanceById(String processInstanceId) {
        return runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
    }

    private HistoricProcessInstance getHistoricProcessInstanceById(String processInstanceId) {
        return historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
    }
}
