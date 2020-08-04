package com.flowable.core.vo;

import com.flowable.core.enums.ResponseCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: yuegc
 * @description:
 * @create: 2020/08/04 10:49
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 6738937570503128261L;

    private int code;

    private String msg;

    private T data;

    private static <T> Result<T> success() {
        return new Result<>();
    }

    private static <T> Result<T> success(T data) {
        return new Result<>(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getMsg(), data);
    }

    private static <T> Result<T> fail(ResponseCodeEnum responseCodeEnum) {
        return new Result<>(responseCodeEnum.getCode(), responseCodeEnum.getMsg(), null);
    }
}
