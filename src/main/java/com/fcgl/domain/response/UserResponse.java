package com.fcgl.domain.response;

import lombok.Data;

/**
 * @author furg@senthink.com
 * @date 2019/1/24
 */
@Data
public class UserResponse {

    /**
     * 用户唯一id
     */
    private String uid;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 账号
     */
    private String account;

    /**
     * 角色字符串
     */
    private String role;

    /**
     * 备注
     */
    private String remarks;
}
