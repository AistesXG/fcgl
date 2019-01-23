package com.fcgl.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Token回复对象
 *
 * @author furg@senthink.com
 * @date 2017/11/17
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {

    /**
     * token
     */
    private String token;

    /**
     * 过期时间
     */
    private long expireAt;

    /**
     * 用户角色
     */
    private String role;
}
