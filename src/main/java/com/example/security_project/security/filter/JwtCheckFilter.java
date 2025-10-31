package com.example.security_project.security.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.AccessDeniedException;
import java.util.Map;

import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.Gson;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

// import java.io.IOException;
// import java.io.PrintWriter;
// import java.nio.file.AccessDeniedException;
// import java.util.List;
// import java.util.Map;

// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.web.filter.OncePerRequestFilter;

// import com.example.security_project.dto.MemberDto;
// import com.example.security_project.util.JwtUtil;
// import com.google.gson.Gson;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import lombok.extern.slf4j.Slf4j;

// @Slf4j
// public class JwtCheckFilter extends OncePerRequestFilter {

//     @Override
//     protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

//         String paths = request.getRequestURI();
//         String method = request.getMethod();
//         log.info("uri : {}, method : {}", paths, method);

//         if (method.equalsIgnoreCase("OPTIONS") || paths.equals("/api/v1/members/login") || paths.startsWith("/api/v1/refresh")) {
//             return true;
//         }

//         return false; // doFilterInternal 실행
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//         throws ServletException, IOException {
        
//         String authHeader = request.getHeader("Authorization");

//         log.info("Authorization Header: {}", authHeader);

//         try{
//             // Bearer[공백][JWT 토큰]
//             String accessToken = authHeader.substring(7);

//             Map<String, Object> claims = JwtUtil.validateToken(accessToken);
//             log.info("claims: {}", claims);

//             String email = (String) claims.get("email");
//             String password = (String) claims.get("password");
//             String nickName = (String) claims.get("nickName");
//             List<String> roleNames = (List<String>) claims.get("roleNames");

//             MemberDto memberDto = new MemberDto(email, password, nickName, roleNames);

//             UsernamePasswordAuthenticationToken authenticationToken =
//                 new UsernamePasswordAuthenticationToken(memberDto, password, memberDto.getAuthorities());

//             SecurityContextHolder.getContext().setAuthentication(authenticationToken);

//             filterChain.doFilter(request, response);

//         } catch(Exception ex) {
//             log.error("error: {}", ex.getMessage());

//             Throwable cause = ex.getCause();
//             if (cause instanceof AccessDeniedException) {
//                 throw (AccessDeniedException) cause;
//             } else {
//                 Gson gson = new Gson();
//                 String jsonStr = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));

//                 response.setContentType("application/json; charset=utf-8");
//                 PrintWriter pw = response.getWriter();
//                 pw.println(jsonStr);
//                 pw.close();
//             }
//         }
//     }

    
// }
@Slf4j
public class JwtCheckFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String method = request.getMethod();
        String paths = request.getRequestURI();
        log.info("method: {}", method);
        log.info("paths: {}", paths);

        if (method.equalsIgnoreCase("OPTIONS") || paths.equals("/api/v1/members/login") || paths.startsWith("/api/v1/refresh")) {
            return true;
        }

        return false; // doFilterInternal 실행
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        
        
            try{
                filterChain.doFilter(request, response);
            } catch(Exception ex) {
                
                Throwable cause = ex.getCause();
                if (cause instanceof AccessDeniedException) {
                    
                } else {
                    Gson gson = new Gson();
                    String jsonStr = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));

                    response.setContentType("application/json; charset=utf-8");
                    PrintWriter pw = response.getWriter();
                    pw.println(jsonStr);
                    pw.close();
                }
            }

        }
}