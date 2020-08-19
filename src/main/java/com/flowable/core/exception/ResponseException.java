package com.flowable.core.exception;

import com.flowable.core.enums.ResponseEnum;
import lombok.Data;

/**
 * @author: yuegc
 * @description:
 * @create: 2020/08/04 09:43
 */
@Data
public class ResponseException extends RuntimeException{
    private static final long serialVersionUID = 6621848193043801862L;

    private int code;

    private String msg;

    public ResponseException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseException(ResponseEnum responseEnum) {
       this(responseEnum.getCode(), responseEnum.getMsg());
    }
}
