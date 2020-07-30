package com.flowable.core.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: yuegc
 * @description:
 * @create: 2020/07/30 14:30
 */
@Data
public class SaveFormDto implements Serializable {
    private static final long serialVersionUID = 6097048714225526497L;

    private String id;

    private String name;

    private String key;

    private String category;

    private String formJson;

    private String description;
}
