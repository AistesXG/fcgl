package com.fcgl.auth;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fcgl.common.converter.Converter;
import com.fcgl.domain.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * 注册用户时，接口传递的数据对象
 *
 * @author furg@senthink.com
 * @date 2017/11/16
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RegisterUserRequest {

    /**
     * 姓名
     */
    @NotBlank
    @Size(min = 1, max = 20)
    private String name;

    /**
     * 账号
     */
    @NotBlank
    @Size(min = 1, max = 60)
    private String account;

    /**
     * 手机号
     */
    @NotBlank
    @Pattern(regexp = "^1[34578]\\d{9}$")
    private String mobile;

    /**
     * email
     */
    @Email
    private String email;

    /**
     * 密码
     */
    @NotBlank
    private String password;

    /**
     * 备注
     */
    private String remarks;


    public static User convertTo(RegisterUserRequest userDTO) {
        UserConverter converter = new UserConverter();
        return converter.forward(userDTO);
    }

    private static class UserConverter implements Converter<RegisterUserRequest, User> {
        @Override
        public User forward(RegisterUserRequest userDTO) {
            User user = new User();
            BeanUtils.copyProperties(userDTO, user);
            return user;
        }

        @Override
        public RegisterUserRequest backward(User user) {
            throw new AssertionError("backward is not support");
        }
    }
}
