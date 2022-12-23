package com.lin.common.security.handler;


import com.lin.exception.AuthException;
import com.lin.exception.CodeException;
import com.lin.exception.TokenTimeOutException;
import com.lin.exception.UserNamePassWordException;
import com.lin.utils.R;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AuthException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object handleNotPermissionException(Exception e, HttpServletRequest request) {
        e.printStackTrace();
        return R.unauthz();
    }

    @ExceptionHandler(value = UserNamePassWordException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object UserNamePassWordException(Exception e, HttpServletRequest request) {
        e.printStackTrace();
        return R.username();
    }

    @ExceptionHandler(value = TokenTimeOutException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object timeException(Exception e, HttpServletRequest request) {   //token 过期
        e.printStackTrace();
        return R.unlogin();
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object e(Exception e, HttpServletRequest request) {
        e.printStackTrace();
        return R.fail(-1, e.getMessage());
    }


    @ExceptionHandler(value = CodeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object CodeException(Exception e, HttpServletRequest request) {
        e.printStackTrace();
        return R.captcha();
    }


}
