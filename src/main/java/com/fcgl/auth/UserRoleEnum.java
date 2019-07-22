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
    ROLE_TEACHER,

    /**
     * 超级管理员用户
     */
    ROLE_SUPER_ROOT,

    /**
     * 管理员用户
     */
    ROLE_ROOT,

    /**
     * 学生
     */
    ROLE_STUDENT;

    public static final String ROLE_TEACHER_VALUE = "ROLE_TEACHER";
    public static final String ROLE_SUPER_ROOT_VALUE = "ROLE_SUPER_ROOT";
    public static final String ROLE_ROOT_VALUE = "ROLE_ROOT";
    public static final String ROLE_STUDENT_VALUE = "ROLE_STUDENT";
}