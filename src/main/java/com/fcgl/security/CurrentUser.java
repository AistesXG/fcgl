package com.fcgl.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/**
 * @author furg@senthink.com
 * @date 2018/11/8
 */
public class CurrentUser implements UserDetails {

    /**
     * 用户的id
     */
    private final String id;

    private final String name;

    private final String mobile;

    private final String username;

    private final String password;

    private final Date lastPwdResetDate;

    private final String role;

    private final Collection<? extends GrantedAuthority> authorities;

    private final boolean enabled;

    public CurrentUser(String id, String name, String mobile, String username, String password, Date lastPwdResetDate, String role, boolean enabled) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.username = username;
        this.password = password;
        this.lastPwdResetDate = lastPwdResetDate;
        this.role = role;
        this.authorities = AuthorityUtils.createAuthorityList(role);
        this.enabled = enabled;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public Date getLastPwdResetDate() {
        return lastPwdResetDate;
    }

    public String getRole() {
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
