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
public enum ResponseEnum {
    SUCCESS(1, "成功"),
    FORM_ABSENT(2, "表单数据为空，请先设计表单并成功保存，再进行发布"),
    BPMN_ABSENT(3, "流程数据为空，请先设计流程并成功保存，再进行发布"),
    ;

    private final int code;

    private final String msg;
}
