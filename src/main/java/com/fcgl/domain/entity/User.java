package com.fcgl.domain.entity;


import com.fcgl.common.entity.BornableEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author furg@senthink.com
 * @date 2018/11/7
 */
@Entity
@Table(name = "user", indexes = {
        @Index(name = "idx_users_uid", columnList = "uid", unique = true),
        @Index(name = "idx_users_mobile", columnList = "mobile", unique = true),
        @Index(name = "idx_users_email", columnList = "email", unique = true),
        @Index(name = "idx_users_account", columnList = "account", unique = true)
})
@Getter
@Setter
public class User extends BornableEntity<Long> {

    /**
     * 用户唯一id
     */
    @Column(name = "uid", nullable = false)
    private String uid;

    /**
     * 姓名
     */
    @Column(name = "user_name", nullable = false)
    private String name;

    /**
     * 账号
     */
    @Column(name = "account", nullable = false)
    private String account;

    /**
     * 手机号
     */
    @Column(name = "mobile", nullable = false)
    private String mobile;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 密码
     */
    @Column(name = "pwd", nullable = false)
    private String password;

    /**
     * 最近一次重置密码的时间
     */
    @Column(name = "last_reset_date")
    private Date lastPwdRestDate;

    /**
     * 角色字符串
     */
    @Column(name = "user_role", nullable = false)
    private String role;

    /**
     * 启用/禁用
     */
    @Column(name = "enable")
    private boolean enable;

    /**
     * 备注
     */
    private String remarks;
}
