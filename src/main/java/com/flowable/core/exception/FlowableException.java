package com.flowable.core.exception;

import com.flowable.core.enums.ResponseCodeEnum;
import lombok.Data;

/**
 * @author: yuegc
 * @description:
 * @create: 2020/08/04 09:43
 */
@Data
public class FlowableException extends RuntimeException{
    private static final long serialVersionUID = 6621848193043801862L;

    private int code;

    private String msg;

    public FlowableException() {
    }

    public FlowableException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public FlowableException(ResponseCodeEnum responseCodeEnum) {
       this(responseCodeEnum.getCode(), responseCodeEnum.getMsg());
    }
}
