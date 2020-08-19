package com.flowable.core.vo;

import com.flowable.core.enums.ResponseEnum;
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

    public static <T> Result<T> success() {
        return new Result<>();
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), data);
    }

    public static <T> Result<T> fail(ResponseEnum responseEnum) {
        return new Result<>(responseEnum.getCode(), responseEnum.getMsg(), null);
    }
}
