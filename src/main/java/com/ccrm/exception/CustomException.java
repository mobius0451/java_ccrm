package com.ccrm.exception;

/**
 * @CreateTime: 2022-11-07 14:21
 * @Description: 自定义异常捕捉器
 */
public class CustomException extends RuntimeException {

    public CustomException(String message){
        super(message);
    }

}
