package com.flowable.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: yuegc
 * @description:
 * @create: 2020/08/04 09:45
 */
@Getter
@AllArgsConstructor
public enum FlowableEnum {
    SUCCESS(1, "");

    private final int code;

    private final String msg;
}
