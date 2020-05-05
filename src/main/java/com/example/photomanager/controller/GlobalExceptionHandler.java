package com.example.photomanager.controller;

import com.example.photomanager.common.JsonWrapper;
import com.example.photomanager.common.KnownException;
import com.example.photomanager.enums.ExceptionEnum;
import com.qiniu.util.Json;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 捕获全局异常
 * @author alex
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理未知异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 500状态码
    @ExceptionHandler(Exception.class)
    public JsonWrapper<String> handleUnknownException(Exception e, HttpServletRequest request) {
        int errorCode = ExceptionEnum.UNKNOWN_EXCEPTION.getErrorCode();
        String errorMessage = ExceptionEnum.UNKNOWN_EXCEPTION.getErrorMsg();
        String stackTrace = Arrays.toString(e.getStackTrace());
        log.error("url: {}    msg: {}", request.getRequestURL(), stackTrace);
        return new JsonWrapper<>(errorCode, errorMessage);
    }

    /**
     * 处理已知异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400状态码
    @ExceptionHandler(KnownException.class)
    public JsonWrapper<String> handleKnownException(KnownException e, HttpServletRequest request) {
        String stackTrace = Arrays.toString(e.getStackTrace());
        log.error("url: {}    msg: {}", request.getRequestURL(), e.getMessage() + stackTrace);
        return new JsonWrapper<>(e.getErrorCode(), "已知异常: " + e.getMessage() + stackTrace);
    }

    /**
     * 对于@Validated 普通参数(非 java bean)校验出错时抛出的异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public JsonWrapper<String> handleConstraintViolationException(ConstraintViolationException e) {
        int errorCode = ExceptionEnum.INVALID_PARAM.getErrorCode();
        String errorMessage = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        return new JsonWrapper<>(errorCode, errorMessage);
    }

    /**
     * 对于@Validated 请求体绑定到java bean上失败时抛出的异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonWrapper<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        int errorCode = ExceptionEnum.INVALID_PARAM.getErrorCode();
        String errorMessage = e.getBindingResult().toString();
        return new JsonWrapper<>(errorCode, errorMessage);
    }

    /**
     * 对于@Valid 请求参数绑定到java bean上失败时抛出的异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public JsonWrapper<String> handleBindException(BindException e) {
        int errorCode = ExceptionEnum.INVALID_PARAM.getErrorCode();
        String errorMessage = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return new JsonWrapper<>(errorCode, errorMessage);
    }


    /**
     * 登录时 用户名不存在抛出的异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnknownAccountException.class)
    public JsonWrapper<String> handleLoginException(UnknownAccountException e){
        int errorCode = ExceptionEnum.NOT_REGISTER.getErrorCode();
        return new JsonWrapper<>(errorCode,"用户名或密码不正确");
    }

    /**
     * 登录时 密码不正常抛出的异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IncorrectCredentialsException.class)
    public JsonWrapper<String> handleIncorrectException(IncorrectCredentialsException e){
        int errorCode = ExceptionEnum.NOT_REGISTER.getErrorCode();
        return new JsonWrapper<>(errorCode,"用户名或密码不正确");
    }
}
