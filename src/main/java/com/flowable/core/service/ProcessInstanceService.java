package com.flowable.core.service;

/**
 * @Description:  
 * @Param: 
 * @return: 
 * @Author: yuegc
 * @Date: 2020/8/3 15:00:02
 */ 
public interface ProcessInstanceService {
    void processInstanceList();

    void startProcessInstance();

    void stopProcessInstance();

    void deleteProcessInstance();

    void suspendProcessInstance();
}
