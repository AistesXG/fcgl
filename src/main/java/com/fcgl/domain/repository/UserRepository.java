package com.fcgl.domain.repository;


import com.fcgl.domain.entity.Dorm;
import com.fcgl.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

/**
 * @author furg@senthink.com
 * @date 2018/11/7
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor {

    List<User> findByAccountOrMobileOrEmail(String account, String mobile, String email);

    long countByAccountOrMobileOrEmail(String account, String mobile, String email);

    Optional<User> findTopByUid(String uid);

    List<User> findByMobile(String mobile);

    List<User> findByAccount(String account);

    List<User> findByEmail(String email);

    void deleteAllByUidIn(List<String> uids);

    List<User> findAllByUidIn(List<String> uids);

    long countByDorm(Dorm dorm);
}
