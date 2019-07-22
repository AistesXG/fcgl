package com.fcgl.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author furg@senthink.com
 * @date 2017/11/15
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.prefix}")
    private String tokenPrefix;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = httpServletRequest.getHeader(tokenHeader);
        //建立socket链接时获取token
        final String requestTokenParam = httpServletRequest.getParameter(tokenHeader);

        String authToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith(tokenPrefix)) {
            authToken = requestTokenHeader.substring(tokenPrefix.length());
        } else if (requestTokenParam != null && requestTokenParam.startsWith(tokenPrefix)) {
            authToken = requestTokenParam.substring(tokenPrefix.length());
        }

        String username;
        try {
            if (authToken == null) {
                username = null;
            } else {
                username = jwtTokenUtils.getUsernameFromToken(authToken);
            }
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                LOGGER.error("get username form token error {}", e.getMessage());
            } else if (e instanceof ExpiredJwtException) {
                LOGGER.error("the token is expired {}", authToken);
            } else {
                LOGGER.error("Error to parse token", e);
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails;
            try {
                userDetails = userDetailsService.loadUserByUsername(username);
            } catch (Exception e) {
                LOGGER.error("Error to load user", e);
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
            if (!userDetails.isEnabled()) {
                throw new DisabledException("用户已被禁用！");
            }

            if (jwtTokenUtils.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
