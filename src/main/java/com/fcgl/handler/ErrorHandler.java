package com.fcgl.handler;


import com.fcgl.response.CodeMsgErrorResponse;
import com.fcgl.common.exception.AuthenticationException;
import com.fcgl.common.exception.BusinessException;
import com.fcgl.common.exception.DataAccessException;
import com.fcgl.messages.CodeMsg;
import com.fcgl.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author furg@senthink.com
 * @date 2017/11/17
 */
@RestControllerAdvice(basePackages = {"com.springboot.framework.controller"})
public class ErrorHandler {

    @Autowired
    private CodeMsg codeMsg;

    /**
     * DataAccessException异常处理
     *
     * @param e {@link DataAccessException}
     * @return responseEntity
     */
    @ExceptionHandler(value = DataAccessException.class)
    public ResponseEntity<?> handleDataAccessException(DataAccessException e) {
        ApiResponse response = new CodeMsgErrorResponse<>(codeMsg.dataAccessExceptionCode(),
                codeMsg.dataAccessExceptionMsg(), e.getMessage());

        Throwable throwable = e;
        while (throwable.getCause() != null) {
            if (throwable.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) {
                response = new CodeMsgErrorResponse<>(codeMsg.isUsingCode(), codeMsg.isUsingMsg(), throwable.getMessage());
                return ResponseEntity.status(HttpStatus.OK).headers(jsonHeader())
                        .body(response);
            }
            throwable = throwable.getCause();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(jsonHeader())
                .body(response);
    }


    /**
     * BusinessException异常处理
     *
     * @param e {@link BusinessException}
     * @return responseEntity
     */
    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException e) {
        ApiResponse response = new CodeMsgErrorResponse<>(e.getCode(), e.getMsg(), e.getMessage());
        return ResponseEntity.status(HttpStatus.OK).headers(jsonHeader()).body(response);
    }

    /**
     * AuthenticationException异常处理
     *
     * @param e ${@link AuthenticationException}
     * @return
     */
    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException e) {
        ApiResponse response = new CodeMsgErrorResponse<>(codeMsg.authenticationExceptionCode(),
                codeMsg.authenticationExceptionMsg(),
                e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).headers(jsonHeader()).body(response);
    }

    /**
     * IllegalArgumentException异常处理
     *
     * @param e {@link IllegalArgumentException}
     * @return responseEntity
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        ApiResponse codeMsgResponse = new CodeMsgErrorResponse<>(codeMsg.illegalArgumentCode(),
                codeMsg.illegalArgumentMsg(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(jsonHeader()).body(codeMsgResponse);
    }

    /**
     * BindException异常处理
     *
     * @param e {@link BindException}
     * @return responseEntity
     */
    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<?> handleBindException(BindException e) {

        List<FieldError> errors = e.getFieldErrors();
        List<String> fields = errors
                .stream()
                .map(fieldError -> fieldError.getField() + ":" + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        ApiResponse codeMsgErrorResponse = new CodeMsgErrorResponse<>(codeMsg.bindExceptionCode(),
                codeMsg.bindExceptionMsg(), fields);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(jsonHeader()).body(codeMsgErrorResponse);
    }

    /**
     * MethodArgumentNotValidException异常处理
     *
     * @param e {@link MethodArgumentNotValidException}
     * @return responseEntity
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        String fields = errors
                .stream()
                .map(fieldError -> fieldError.getField() + ":" + fieldError.getDefaultMessage() + ",")
                .reduce("", String::concat);
        ApiResponse codeMsgErrorResponse = new CodeMsgErrorResponse<>(codeMsg.methodArgumentCode(),
                codeMsg.methodArgumentMsg(), fields);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(jsonHeader()).body(codeMsgErrorResponse);
    }

    /**
     * 设置Http头的contentType为Json
     *
     * @return httpHeaders
     */
    private HttpHeaders jsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return httpHeaders;
    }

}
