package com.fcgl.security;


import com.fcgl.domain.entity.User;
import org.springframework.stereotype.Service;

/**
 * CurrentUserService
 *
 * @author furg@senthink.com
 * @date 2017/11/14
 */
@Service
public class CurrentUserService {

    /**
     * 创建CurrentUser实例
     *
     * @param user {@link User}
     * @return {@link CurrentUser}实例
     */
    public CurrentUser convert(User user) {


        return new CurrentUser(user.getUid(),
                user.getName(),
                user.getMobile(),
                user.getAccount(),
                user.getPassword(),
                user.getLastPwdRestDate(),
                user.getRole(),
                user.isEnable());
    }
}
