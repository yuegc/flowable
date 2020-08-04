package com.flowable.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: yuegc
 * @description:
 * @create: 2020/08/04 10:05
 */
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum {
    SUCCESS(1, "成功");

    private final int code;

    private final String msg;
}
