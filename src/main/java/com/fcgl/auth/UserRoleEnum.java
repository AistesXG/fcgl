package com.fcgl.auth;

/**
 * 用户角色枚举
 *
 * @author furg@senthink.com
 * @date 2018/03/08
 */
public enum UserRoleEnum {

    /**
     * 教师
     */
    TEACHER,

    /**
     * 超级管理员用户
     */
    SUPER_ROLE_ROOT,

    /**
     * 管理员用户
     */
    ROLE_ROOT;

    public static final String TEACHER_VALUE = "TEACHER";
    public static final String SUPER_ROLE_ROOT_VALUE = "SUPER_ROLE_ROOT";
    public static final String ROLE_ROOT_VALUE = "ROLE_ROOT";

}