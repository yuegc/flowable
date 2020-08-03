package com.flowable.core.service;

/**
 * @author: yuegc
 * @description:
 * @create: 2020/01/19 17:44
 */
public interface ProcessInstanceService {
    void processInstanceList();

    void startProcessInstance();

    void stopProcessInstance();

    void deleteProcessInstance();

    void suspendProcessInstance();
}
