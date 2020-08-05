package com.flowable.core.service;

/**
 * @Description:  
 * @Param: 
 * @return: 
 * @Author: yuegc
 * @Date: 2020/8/3 15:00:02
 */ 
public interface ProcessInstanceService {
    void instanceList();

    void startInstance();

    void stopInstance();

    void deleteInstance();

    void suspendInstance();

    void activeInstance();
}
