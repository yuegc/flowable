package com.flowable.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.flowable.core.entity.FormModel;

public interface FormModelDao extends JpaRepository<FormModel, String>, JpaSpecificationExecutor<FormModel> {
    FormModel getFormModelById(String Id);
}