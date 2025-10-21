package com.example.security_project.security.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
            log.error("Access Denied: {}", accessDeniedException.getMessage());

            Gson gson = new Gson();

            String jsonStr = gson.toJson(Map.of("error", "ACCESS_DENIED"));
            
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(HttpStatus.FORBIDDEN.value());

            PrintWriter pw = response.getWriter();
            pw.println(jsonStr);
            pw.close();
    }
    
    
}
