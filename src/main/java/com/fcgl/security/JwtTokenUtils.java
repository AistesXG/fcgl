package com.fcgl.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Jwt Token 工具类，用来帮助生成和解析Jwt token
 *
 * @author furg@senthink.com
 * @date 2017/11/15
 */
@Component
public class JwtTokenUtils implements Serializable {

    private static final String AUDIENCE = "web";

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;
    @Value("${jwt.prefix}")
    private String tokenPrefix;

    /**
     * 从token中解析出username
     *
     * @param token token
     * @return string
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * 直接从Authorization请求头中解析出username
     *
     * @param authorizationHeader Authorization请求头
     * @return
     */
    public String getUsernameFromAuthorizationHeader(String authorizationHeader) {
        String token = authorizationHeader.substring(tokenPrefix.length());
        return getUsernameFromToken(token);
    }

    /**
     * 从token中解析出token的签发时间
     *
     * @param token token
     * @return date
     */
    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    /**
     * 从token中解析出token的过期时间
     *
     * @param token token
     * @return date
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * 从token中解析出目标受众
     *
     * @param token token
     * @return string
     */
    public String getAudienceFromToken(String token) {
        return getClaimFromToken(token, Claims::getAudience);
    }

    /**
     * 生成Token
     *
     * @param currentUser
     * @return string
     */
    public String generateToken(CurrentUser currentUser) {
        Map<String, Object> claims = new HashMap<>(10);
        claims.put("role", currentUser.getRole());
        return doGenerateToken(claims, currentUser.getUsername());
    }

    /**
     * 判断Token是否能被刷新
     *
     * @param token        token
     * @param lastPwdReset lastPwdReset
     * @return boolean
     */
    public boolean canTokenBeRefreshed(String token, Date lastPwdReset) {
        final Date created = getIssuedAtDateFromToken(token);
        return !isCreatedBeforeLastPwdReset(created, lastPwdReset) && !isTokenExpired(token);
    }

    /**
     * 刷新Token
     *
     * @param token 旧的token
     * @return string
     */
    public String refreshToken(String token) {
        final Date createdDate = new Date();
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 校验Token
     *
     * @param token       token
     * @param userDetails userDetails
     * @return boolean
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        CurrentUser user = (CurrentUser) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);

        return (username.equals(user.getUsername())
                && !isTokenExpired(token)
                && !isCreatedBeforeLastPwdReset(created, user.getLastPwdResetDate()));
    }

    /**
     * 解析声明数据
     *
     * @param token          token
     * @param claimsResolver resolver
     * @param <T>            T
     * @return T
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Jwt Token 生成
     *
     * @param claims   claims
     * @param subject  subject
     * @return string
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createDate = new Date();
        final Date expirationDate = calculateExpirationDate(createDate);

        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setAudience(AUDIENCE)
                .setIssuedAt(createDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }


    /**
     * 计算token过期时间
     *
     * @param createdDate 创建时间
     * @return date
     */
    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }

    /**
     * 从jwt token中解析出存入的全部数据声明
     *
     * @param token token
     * @return claims
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 判断Token是否过期
     *
     * @param token token
     * @return boolean
     */
    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 判断Token是否是重置密码之前创建的
     *
     * @param created      创建时间
     * @param lastPwdReset 重置密码时间
     * @return boolean
     */
    private boolean isCreatedBeforeLastPwdReset(Date created, Date lastPwdReset) {
        return (lastPwdReset != null && created.before(lastPwdReset));
    }
}
