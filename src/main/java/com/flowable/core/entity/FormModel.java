package com.flowable.core.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ACT_FO_FORM_MODEL")
@Data
public class FormModel implements Serializable {
    private static final long serialVersionUID = -1410031555211643335L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "ID_", length = 64, nullable = false)
    private String id;

    @Column(name = "REV_", length = 11)
    private Integer rev;

    @Column(name = "NAME_")
    private String name;

    @Column(name = "KEY_")
    private String key;

    @Column(name = "CATEGORY_")
    private String category;

    @Column(name = "CREATE_TIME_", length = 3)
    private Timestamp createTime;

    @Column(name = "LAST_UPDATE_TIME_", length = 3)
    private Timestamp lastUpdateTime;

    @Column(name = "FORM_JSON_", length = 5000)
    private String formJson;

    @Column(name = "VERSION_", length = 11)
    private Integer version;

    @Column(name = "DESCRIPTION_")
    private String description;

    @Column(name = "DEPLOYMENT_ID_", length = 64)
    private String deploymentId;

    @Column(name = "TENANT_ID_")
    private String tenantId;
}