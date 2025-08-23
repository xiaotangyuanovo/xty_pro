package com.cetc38.studysystem.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 生成JWT令牌
     */
    public String generateToken(String username, Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);
        claims.put("role", "STUDENT");
        
        return createToken(claims, username);
    }

    /**
     * 创建令牌
     */
    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        
        // 使用更安全的密钥生成方式
        SecretKey key = generateSecureKey();
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
    
    /**
     * 生成安全的密钥
     */
    private SecretKey generateSecureKey() {
        try {
            // 尝试将密钥作为Base64解码
            byte[] keyBytes = java.util.Base64.getDecoder().decode(secret);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (IllegalArgumentException e) {
            // 如果不是Base64格式，直接使用原始字符串
            if (secret.length() >= 32) {
                return Keys.hmacShaKeyFor(secret.getBytes());
            } else {
                // 如果密钥长度不够，使用Keys.secretKeyFor生成安全的密钥
                return Keys.secretKeyFor(SignatureAlgorithm.HS512);
            }
        }
    }

    /**
     * 从令牌中获取用户名
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * 从令牌中获取用户ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("userId", Long.class);
    }

    /**
     * 从令牌中获取过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * 从令牌中获取指定声明
     */
    public <T> T getClaimFromToken(String token, java.util.function.Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 从令牌中获取所有声明
     */
    private Claims getAllClaimsFromToken(String token) {
        SecretKey key = generateSecureKey();
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 检查令牌是否过期
     */
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 验证令牌
     */
    public Boolean validateToken(String token, String username) {
        final String tokenUsername = getUsernameFromToken(token);
        return (username.equals(tokenUsername) && !isTokenExpired(token));
    }

    /**
     * 获取令牌剩余有效期（毫秒）
     */
    public Long getTokenRemainingTime(String token) {
        try {
            Date expiration = getExpirationDateFromToken(token);
            Date now = new Date();
            return Math.max(0, expiration.getTime() - now.getTime());
        } catch (Exception e) {
            return 0L;
        }
    }
}

