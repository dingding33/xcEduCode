package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常捕获类
 */
@ControllerAdvice
public class ExceptionCatch {


    private static final Logger logger = LoggerFactory.getLogger(ExceptionCatch.class);

    /**
     * 使用 Exceptions 存放异常类型和错误代码的映射，ImmutableMap 一旦创建不可改变，并且线程安全
     */
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS;
    /** 使用 builder 来构建一个异常类型和错误代码的异常 */
    protected static ImmutableMap.Builder<Class<? extends Throwable>,ResultCode> builder = ImmutableMap.builder();


    static {
        // 基础异常类型的判断
        // 反序列化异常
        builder.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);
    }

    /**
     * 捕获 CustomException
     * @param e
     * @return
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException e) {
        logger.error("catch exception : {}\r\nexception: ",e.getMessage(),e);
        ResultCode resultCode = e.getResultCode();
        return new ResponseResult(resultCode);

    }

    /**
     * 捕获 Exception
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception e) {
        logger.error("catch exception : {}\r\nexception: ",e.getMessage(),e);
        if (EXCEPTIONS == null) {
            EXCEPTIONS = builder.build();
        }
        final ResultCode resultCode = EXCEPTIONS.get(e.getClass());
        final ResponseResult responseResult;
        if (resultCode != null) {
            responseResult = new ResponseResult(resultCode);
        } else {
            responseResult = new ResponseResult(CommonCode.SERVER_ERROR);
        }
            return responseResult;
    }
}
