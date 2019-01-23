package com.fcgl.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录请求时，接口传递的数据对象
 *
 * @author furg@senthink.com
 * @date 2017/11/17
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class LoginRequest {


    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
