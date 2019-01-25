package com.fcgl.domain.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author furg@senthink.com
 * @date 2019/1/24
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserRequest {

    /**
     * name
     */
    @NotBlank
    private String name;

    /**
     * 账号
     */
    @NotBlank
    private String account;

    /**
     * 手机号
     */
    @NotBlank
    @Pattern(regexp = "^1[34578]\\d{9}$")
    private String mobile;

    /**
     * 邮箱
     */
    @NotBlank
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 角色
     */
    @NotBlank
    private String role;

    /**
     * 备注
     */
    private String remarks;
}
