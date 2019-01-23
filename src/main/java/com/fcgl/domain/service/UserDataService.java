package com.fcgl.domain.service;

import com.fcgl.common.exception.DataAccessException;
import com.fcgl.domain.entity.User;
import com.fcgl.domain.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

/**
 * @author furg@senthink.com
 * @date 2018/11/7
 */
@Service
public class UserDataService {

    @Autowired
    private UserRepository repository;


    /**
     * 查找用户信息
     *
     * @param username
     * @return
     */
    public Optional<User> loadUser(String username) {
        if (StringUtils.isBlank(username)) {
            return Optional.empty();
        }
        List<User> users;
        try {
            users = repository.findByAccountOrMobileOrEmail(username, username, username);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
        if (users == null || users.size() == 0) {
            return Optional.empty();
        }

        return Optional.of(users.get(0));
    }

    /**
     * 判断用户是否存在
     *
     * @param account
     * @param mobile
     * @param email
     * @return
     */
    public boolean isUserExist(String account, String mobile, String email) {
        long result;
        try {
            result = repository.countByAccountOrMobileOrEmail(account, mobile, email);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }

        return result > 0;
    }


    /**
     * 保存用户信息
     *
     * @param user
     * @return
     */
    public User saveUser(User user) {
        try {
            return repository.save(user);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }
}
