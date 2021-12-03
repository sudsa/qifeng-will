package com.bpaas.doc.framework.web.controller;

import com.bpaas.doc.framework.web.view.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bpaas.doc.framework.base.command.NotProguard;
import com.bpaas.doc.framework.base.common.BaseConstant;
import com.bpaas.doc.framework.base.exception.BpaasException;
import com.bpaas.doc.framework.base.exception.BpaasRuntimeException;
import com.bpaas.doc.framework.base.exception.OAuthException;

/**
 * 将异常更改为json格式
 * @author huyang
 * @date 2015年11月19日 
 *
 */
@NotProguard
@ControllerAdvice
public class ErrorControllerAdvice {

    private static Logger log = LoggerFactory.getLogger(ErrorControllerAdvice.class);

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse errorResponse(IllegalArgumentException exception) {
        log.error(exception.getMessage(), exception);
        return new BaseResponse(false, exception.getMessage(), BaseConstant.ResponseCode.ILLEGAL_ARGUMENT);
    }
    
    @ExceptionHandler(value = DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse errorResponse(DuplicateKeyException exception) {
        log.error(exception.getMessage(), exception);
        return new BaseResponse(false, "主键重复", BaseConstant.ResponseCode.ILLEGAL_ARGUMENT);
    }

    @ExceptionHandler(value = OAuthException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse errorResponse(OAuthException exception) {
        log.error(exception.getMessage(), exception);
        return new BaseResponse(false, exception.getMessage(), BaseConstant.ResponseCode.ILLEGAL_ARGUMENT);
    }

    @ExceptionHandler(value = BpaasException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse errorResponse(BpaasException exception) {
        log.error(exception.getMessage(), exception);
        return new BaseResponse(false, exception.getMessage(), BaseConstant.ResponseCode.BUSINESS_ERROR);
    }

    @ExceptionHandler(value = BpaasRuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse errorResponse(BpaasRuntimeException exception) {
        log.error(exception.getMessage(), exception);
        return new BaseResponse(false, exception.getMessage(), BaseConstant.ResponseCode.BUSINESS_ERROR);
    }
}
