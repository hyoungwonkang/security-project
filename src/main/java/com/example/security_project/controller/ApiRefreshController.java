package com.example.security_project.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.security_project.exception.CustomJwtException;
import com.example.security_project.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ApiRefreshController {
    
    @GetMapping("/refresh")
    public Map<String, Object> refresh(@RequestHeader("Authorization") String authHeader,
        @RequestParam("refreshToken") String refreshToken) {
            
            if (refreshToken == null || authHeader.length() < 7) {
                throw new CustomJwtException("INVALID_AUTH");
            }

            String accessToken = authHeader.substring(7);
            // 엑세스 토큰이 만료되지 않은 경우
            if (!checkExpiration(accessToken)) {
                return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
            }

            // 엑세스 토큰이 만료된 경우 refreshToken 검증하고 검증이 된 경우 새로운 accessToken 생성
            Map<String, Object> claims = JwtUtil.validateToken(refreshToken);
            log.info("claims: {}", claims);

            String newAccessToken = JwtUtil.generateToken(claims, 1); // 1분

            log.info("exp: {}", claims.get("exp")); // 만료 시간 : 초 단위(sec)

            String newRefreshToken = checkTime((Integer) claims.get("exp")) == true ?
                JwtUtil.generateToken(claims, 16 * 24) : 
                refreshToken;

            return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);
    }

    // 엑세스 토큰의 만료 여부 확인
    private boolean checkExpiration(String accessToken) {
        try {
            JwtUtil.validateToken(accessToken);
        } catch (Exception e) {
            if (e.getMessage().equals("Expired")) {  // CustomJwtException에서 "Expired" 메시지 사용
                return true;
            }
        }
        
        return false;
    }

    // refreshToken 만료 시간이 1시간 미만인지 여부 확인
    private boolean checkTime(Integer exp) {
        
        // 초 -> 밀리초로 변환해서 Date 객체 생성
        Date expDate = new Date(exp * 1000L);

        // 현재 시각과의 차이 계산(ms)
        long gap = expDate.getTime() - System.currentTimeMillis();

        // 밀리초 -> 분으로 변환
        long leftTime = gap / (1000 * 60);

        return leftTime < 60;
    }
}
