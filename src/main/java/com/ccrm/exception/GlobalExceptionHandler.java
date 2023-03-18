package com.ccrm.exception;

import com.ccrm.common.ResultHttpStatus;
import com.ccrm.domain.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @CreateTime: 2022-11-07 14:19
 * @Description: 全局异常捕捉器
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 特定异常
     * @param e
     * @return
     */
    @ExceptionHandler(ArithmeticException.class)
    public ResponseResult handleArithmeticException(ArithmeticException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生特定异常: {}", requestURI, e.getMessage());
        return ResponseResult.error(e.getMessage());
    }

    /**
     * 重复字段异常
     * @param e
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseResult handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e,
                                                                         HttpServletRequest request){
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生异常: {}", requestURI, e.getMessage());
        if (e.getMessage().contains("Duplicate entry")){
            String[] split = e.getMessage().split(" ");
            String msg = split[2] + "已存在！";
            return ResponseResult.error(msg);
        }
        return ResponseResult.error("未知错误！");
    }

    /**
     * 自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public ResponseResult handleCustomException(CustomException e, HttpServletRequest request){
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生自定义异常: {}", requestURI, e.getMessage());
        return ResponseResult.error(e.getMessage());
    }

    /**
     * 权限校验异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseResult handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',权限校验失败'{}'", requestURI, e.getMessage());
        return ResponseResult.error(ResultHttpStatus.FORBIDDEN, "没有权限，请联系管理员授权");
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseResult handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                          HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
        return ResponseResult.error(e.getMessage());
    }


    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseResult handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        return ResponseResult.error(e.getMessage());
    }

    /**
     * 系统全局异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestURI, e);
        return ResponseResult.error(e.getMessage());
    }
}
