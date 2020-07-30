package com.flowable.flowable.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ACT_FO_FORM_MODEL")
@Data
public class FormModel implements Serializable {
    private static final long serialVersionUID = -1410031555211643335L;

    @Id
    @Column(name = "ID_", insertable = false, nullable = false)
    private String id;

    @Column(name = "REV_")
    private Integer rev;

    @Column(name = "NAME_")
    private String name;

    @Column(name = "KEY_")
    private String key;

    @Column(name = "CATEGORY_")
    private String category;

    @Column(name = "CREATE_TIME_")
    private Timestamp createTime;

    @Column(name = "LAST_UPDATE_TIME_")
    private Timestamp lastUpdateTime;

    @Column(name = "FORM_JSON_")
    private String formJson;

    @Column(name = "VERSION_")
    private Integer version;

    @Column(name = "DESCRIPTION_")
    private String description;

    @Column(name = "DEPLOYMENT_ID_")
    private String deploymentId;

    @Column(name = "TENANT_ID_")
    private String tenantId;

    
}