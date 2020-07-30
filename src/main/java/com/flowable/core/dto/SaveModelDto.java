package com.flowable.core.dto;

import lombok.Data;

/**
 * @author: yuegc
 * @description:
 * @create: 2020/07/15 14:31
 */
@Data
public class SaveModelDto {
    String Id;

    String name;

    String key;

    String category;

    String modelXml;
}
