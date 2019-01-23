package com.fcgl.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author furg@senthink.com
 * @date 2017/11/16
 */
@Component
public class CodeMsg {

    @Autowired
    private MessageSource messageSource;

    /**
     * 成功Code
     *
     * @return integer
     */
    public Integer successCode() {
        return Integer.valueOf(message("response.success.code"));
    }

    /**
     * 成功Msg
     *
     * @return string
     */
    public String successMsg() {
        return message("response.success.msg");
    }

    /**
     * 失败Code
     *
     * @return string
     */
    public Integer failureCode() {
        return Integer.valueOf(message("response.failure.code"));
    }

    /**
     * 失败msg
     *
     * @return string
     */
    public String failureMsg() {
        return message("response.failure.msg");
    }

    /**
     * 用户存在code
     *
     * @return integer
     */
    public Integer userExistCode() {
        return Integer.valueOf(message("response.userExist.code"));
    }

    /**
     * 用户存在msg
     *
     * @return string
     */
    public String userExistMsg() {
        return message("response.userExist.msg");
    }

    /**
     * 名字存在code
     *
     * @return integer
     */
    public Integer nameExistCode() {
        return Integer.valueOf(message("response.nameExist.code"));
    }

    /**
     * 名字存在msg
     *
     * @return string
     */
    public String nameExistMsg() {
        return message("response.nameExist.msg");
    }

    /**
     * 登录用户名或密码错误code
     *
     * @return
     */
    public Integer accountErrorCode() {
        return Integer.valueOf(message("response.accountError.code"));
    }

    /**
     * 登录用户名或密码错误msg
     *
     * @return
     */
    public String accountErrorMsg() {
        return message("response.accountError.msg");
    }

    /**
     * 用户不存在code
     *
     * @return
     */
    public Integer accountNotExistCode() {
        return Integer.valueOf(message("response.accountNotExist.code"));
    }

    /**
     * 用户不存在msg
     *
     * @return
     */
    public String accountNotExistMsg() {
        return message("response.accountNotExist.msg");
    }

    /**
     * Token校验错误code
     *
     * @return integer
     */
    public Integer tokenErrorCode() {
        return Integer.valueOf(message("response.tokenError.code"));
    }

    /**
     * Token校验错误msg
     *
     * @return string
     */
    public String tokenErrorMsg() {
        return message("response.tokenError.msg");
    }


    /**
     * 数据库访问异常code
     *
     * @return integer
     */
    public Integer dataAccessExceptionCode() {
        return Integer.valueOf(message("response.dataAccessException.code"));
    }

    /**
     * 数据库访问异常msg
     *
     * @return string
     */
    public String dataAccessExceptionMsg() {
        return message("response.dataAccessException.msg");
    }

    /**
     * 号码错误Code
     *
     * @return
     */
    public Integer isUsingCode() {
        return Integer.valueOf(message("response.isUsing.code"));
    }

    /**
     * 号码错误Msg
     *
     * @return
     */
    public String isUsingMsg() {
        return message("response.isUsing.msg");
    }

    /**
     * 身份认证异常code
     *
     * @return integer
     */
    public Integer authenticationExceptionCode() {
        return Integer.valueOf(message("response.authenticationException.code"));
    }

    /**
     * 身份认证异常code
     *
     * @return string
     */
    public String authenticationExceptionMsg() {
        return message("response.authenticationException.msg");
    }

    /**
     * 非法参数code
     *
     * @return integer
     */
    public Integer illegalArgumentCode() {
        return Integer.valueOf(message("response.illegalArgument.code"));
    }

    /**
     * 非法参数msg
     *
     * @return string
     */
    public String illegalArgumentMsg() {
        return message("response.illegalArgument.msg");
    }

    /**
     * 参数校验异常code
     *
     * @return integer
     */
    public Integer bindExceptionCode() {
        return Integer.valueOf(message("response.bindException.code"));
    }

    /**
     * 参数校验异常msg
     *
     * @return string
     */
    public String bindExceptionMsg() {
        return message("response.bindException.msg");
    }

    /**
     * 方法参数校验异常code
     *
     * @return integer
     */
    public Integer methodArgumentCode() {
        return Integer.valueOf(message("response.methodArgument.code"));
    }

    /**
     * 方法参数校验异常msg
     *
     * @return string
     */
    public String methodArgumentMsg() {
        return message("response.methodArgument.msg");
    }


    /**
     * 读取message
     *
     * @param key key
     * @return string
     */
    private String message(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }
}
