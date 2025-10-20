package com.example.security_project.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.security_project.dto.MemberDto;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

// 인증에 성공한 경우
@Slf4j
public class ApiAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        
        // email
        MemberDto memberDto = (MemberDto) authentication.getPrincipal();
        String email = memberDto.getEmail();

        log.info("---------------------------------------------");
        // Context-Type 설정
        response.setContentType("text/html; charset=utf-8");

        PrintWriter pw = response.getWriter();
        pw.println("<h1>" + email + "님 환영합니다!</h1>");
        pw.close();
    }
    
}
