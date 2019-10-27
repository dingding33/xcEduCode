package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * 异常抛出类
 * @author ydb 
 */
public class ExceptionCast {

    /**
     * 抛出异常
     * @param resultCode
     */
    public static void cast(ResultCode resultCode) {
        throw new CustomException(resultCode);
    }

}
