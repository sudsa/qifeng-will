package com.qifeng.will.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @param <T>
 * @author 杜志诚
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "响应信息主体")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    @Getter
    @Setter
    @ApiModelProperty(value = "返回标记：成功标记=0，失败标记=1")
    private int code;
    @Getter
    @Setter
    @ApiModelProperty(value = "返回信息")
    private String msg;
    @Getter
    @Setter
    @ApiModelProperty(value = "数据")
    private T data;

    public static <T> R<T> ok() {
        return restResult(null, HttpRespMsg.SUCCESS, HttpRespMsg.MSG);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, HttpRespMsg.SUCCESS, HttpRespMsg.MSG);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, HttpRespMsg.SUCCESS, msg);
    }

    public static <T> R<T> failed() {
        return restResult(null, HttpRespMsg.FAIL, HttpRespMsg.FAIL_MSG);
    }

    public static <T> R<T> failed(String msg) {
        return restResult(null, HttpRespMsg.FAIL, msg);
    }

    public static <T> R<T> failed(T data) {
        return restResult(data, HttpRespMsg.FAIL, null);
    }

    public static <T> R<T> failed(Integer code, String msg) {
        return restResult(null, code, msg);
    }


    @SneakyThrows
    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }
}
