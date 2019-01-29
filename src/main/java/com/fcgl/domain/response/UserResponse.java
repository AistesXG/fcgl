package com.fcgl.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fcgl.domain.entity.Campus;
import com.fcgl.domain.entity.Dorm;
import lombok.Data;

import java.util.Set;

/**
 * @author furg@senthink.com
 * @date 2019/1/24
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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
     * 校区
     */
    private Set<Campus> campus;

    /**
     * 宿舍楼
     */
    private Dorm dorm;

    /**
     * 备注
     */
    private String remarks;
}
