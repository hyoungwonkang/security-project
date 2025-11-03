package com.example.security_project.util;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import com.example.security_project.exception.CustomJwtException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
    // 키값은 30자 이상 사용할 것
    private static final String key = "your-very-long-secret-key-32bytes!";

    // JWT 토큰 생성
    public static String generateToken(Map<String, Object> claims, int min) {
        SecretKey secretKey = null;
        
        try {
            secretKey = Keys.hmacShaKeyFor(key.getBytes("utf-8"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return Jwts.builder()
            .setHeader(Map.of("typ","JWT"))
            .setClaims(claims)
            .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
            .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
            .signWith(secretKey)
            .compact();
    }

    // JWT 토큰 검증
    public static Map<String, Object> validateToken(String token) {
        Map<String, Object> claims = null;
        try {
            SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes("utf-8"));

            // parseClaimsJws 사용 - 서명 검증 포함 (보안상 필수)
            claims = Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token)  // JWS = 서명된 토큰 검증
                        .getBody();
        } catch(MalformedJwtException malformedJwtException) {
            throw new CustomJwtException("Malformed"); // 잘못된 형식의 JWT
        } catch(ExpiredJwtException expiredJwtException) {
            throw new CustomJwtException("Expired"); // 만료된 JWT
        } catch(InvalidClaimException invalidClaimException) {
            throw new CustomJwtException("Invalid Claim"); // 잘못된 클레임
        } catch(JwtException jwtException) {
            throw new CustomJwtException("JwtError"); // 서명 검증에 실패
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token: " + e.getMessage());
        }
        return claims;
    }
}