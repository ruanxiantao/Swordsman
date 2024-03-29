package com.swordsman.common.handle;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.sql.parser.ParserException;
import com.swordsman.common.email.MailService;
import com.swordsman.common.exception.CustomException;
import com.swordsman.common.exception.StatusException;
import com.swordsman.common.web.ApiResult;
import com.swordsman.common.web.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.NoSuchElementException;

/**
 * @Author DuChao
 * @Date 2019-10-21 10:44
 * 全局统一异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandle {

    @Autowired
    private MailService mailService;

    /**
     * Exception 异常分类处理
     */
    @ExceptionHandler(Exception.class)
    public ApiResult handleAllException(Exception e) {
        if (e instanceof MethodArgumentNotValidException)   // Valid 校验异常处理
            return bindException(((MethodArgumentNotValidException) e).getBindingResult());
        else if (e instanceof BindException)    // Valid 校验异常处理
            return bindException(((BindException) e).getBindingResult());
        else if (e instanceof NoSuchElementException)   // 空节点数据处理
            return new ApiResult(Status.EMPTY_DATA);
        else if (e instanceof DuplicateKeyException)    // 唯一索引约束处理
            return new ApiResult(Status.UNIQUE_CONSTRAINT);
        else if (e instanceof CustomException)   // 自定义异常处理
            return new ApiResult(e.getMessage());
        else if (e instanceof StatusException){
            return new ApiResult(((StatusException) e).getStatus());
        }  // 自定义状态枚举异常

        else if (e instanceof NoHandlerFoundException){ // 未找到请求路径异常处理
            log.error("【全局异常拦截】NoHandlerFoundException: 请求方法 {}, 请求路径 {}", ((NoHandlerFoundException) e).getRequestURL(), ((NoHandlerFoundException) e).getHttpMethod());
            return new ApiResult(Status.REQUEST_NOT_FOUND);
        }
        else if (e instanceof HttpRequestMethodNotSupportedException){  // 请求方式不支持异常处理
            log.error("【全局异常拦截】HttpRequestMethodNotSupportedException: 当前请求方式 {}, 支持请求方式 {}", ((HttpRequestMethodNotSupportedException) e).getMethod(), JSONUtil.toJsonStr(((HttpRequestMethodNotSupportedException) e).getSupportedHttpMethods()));
            return new ApiResult(Status.HTTP_BAD_METHOD);
        }
        else if (e instanceof ConstraintViolationException) {   // 校验异常处理
            log.error("【全局异常拦截】ConstraintViolationException", e);
            return new ApiResult(CollUtil.getFirst(((ConstraintViolationException) e).getConstraintViolations())
                    .getMessage());
        }
        else if (e instanceof MethodArgumentTypeMismatchException) {    // 请求参数异常处理
            log.error("【全局异常拦截】MethodArgumentTypeMismatchException: 参数名 {}, 异常信息 {}", ((MethodArgumentTypeMismatchException) e).getName(), ((MethodArgumentTypeMismatchException) e).getMessage());
            return new ApiResult(Status.PARAM_NOT_MATCH);
        }
        else if (e instanceof HttpMessageNotReadableException) {    // 参数不能为空异常处理
            log.error("【全局异常拦截】HttpMessageNotReadableException: 错误信息 {}", ((HttpMessageNotReadableException) e).getMessage());
            return new ApiResult(Status.PARAM_NOT_NULL);
        }
        else if (e instanceof SQLException) {    // SQL 异常处理
            log.error("【全局异常拦截】SQLException: 错误信息 {}", e.getMessage());
            return new ApiResult(Status.SQL_ERROR);
        }
        else if (e instanceof NullPointerException) {    // SQL 异常处理
            e.printStackTrace();
            log.error("【全局异常拦截】NullPointerException: 错误信息 {}", e.getMessage());
            return new ApiResult(Status.NULL_POINTER_EXCEPTION);
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        mailService.sendHtmlMail("duchao@baijingins.com","Swordsman 系统异常",sw.toString());

        e.printStackTrace();
        if (e.getMessage() != null)
            return new ApiResult(e.getMessage() + " 系统异常，请及时联系管理员 ");
        return new ApiResult("系统异常，请及时联系管理员");
    }

    private ApiResult bindException(BindingResult e){
        FieldError fieldError = e.getFieldError();
        assert fieldError != null;
        log.error(fieldError.getField() + ":" + fieldError.getDefaultMessage());
        return new ApiResult(fieldError.getField() + ":" + fieldError.getDefaultMessage());
    }
}
