package com.fcgl.security;


import com.fcgl.domain.entity.User;
import com.fcgl.domain.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author furg@senthink.com
 * @date 2017/11/9
 */
@Service
public class CurrentUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDataService userService;
    @Autowired
    private CurrentUserService currentUserService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.loadUser(s)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("%s not found", s)));
        return currentUserService.convert(user);
    }
}
