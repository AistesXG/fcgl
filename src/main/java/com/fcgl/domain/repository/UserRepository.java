package com.fcgl.domain.repository;



import com.fcgl.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author furg@senthink.com
 * @date 2018/11/7
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor {


    /**
     * 根据用户的账号/电话号/邮箱来查找用户信息
     *
     * @param account
     * @param mobile
     * @param email
     * @return
     */
    List<User> findByAccountOrMobileOrEmail(String account, String mobile, String email);

    /**
     * 计算用户的记录数，根据账号，手机号或者email
     *
     * @param account 账号
     * @param mobile  手机号
     * @param email   email
     * @return long
     */
    long countByAccountOrMobileOrEmail(String account, String mobile, String email);
}
