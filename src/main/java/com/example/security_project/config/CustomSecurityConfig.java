package com.example.security_project.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.security_project.security.handler.ApiAuthenticationFailureHandler;
import com.example.security_project.security.handler.ApiAuthenticationSuccessHandler;

// import java.util.Arrays;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// import com.example.security_project.security.filter.JwtCheckFilter;
// import com.example.security_project.security.handler.ApiAuthenticationFailureHandler;
// import com.example.security_project.security.handler.ApiAuthenticationSuccessHandler;
// import com.example.security_project.security.handler.CustomAccessDeniedHandler;

// import lombok.extern.slf4j.Slf4j;

// // Java Config class
// @Slf4j
// @EnableWebSecurity
// @Configuration
// @EnableMethodSecurity // 권한을 사용하기 위한 어노테이션
// public class CustomSecurityConfig {
    
//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//         log.info("------------------------------------------ SecurityFilterChain");
        
//         //1. CORS 설정 활성화
//         http.cors(httpSecurityCorsConfigurer -> {
//             httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()); // DI(의존성 주입) -> CORS 설정 메서드 호출
//         });

//         //2. 세션 비활성화: 클라이언트에서 오는 Session 저장 안하기
//         http.sessionManagement(sessionConfig -> {
//             sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//         });

//         //3. CSRF 비활성화
//         http.csrf(csrf -> csrf.disable());

//         // 폼 기반 로그인 요청 처리
//         http.formLogin(config -> {
//             config.loginProcessingUrl("/api/v1/members/login");
//             config.usernameParameter("email");
//             // config.passwordParameter("password");
//             config.successHandler(new ApiAuthenticationSuccessHandler());
//             config.failureHandler(new ApiAuthenticationFailureHandler());
//         });

//         http.addFilterBefore(new JwtCheckFilter(), UsernamePasswordAuthenticationFilter.class);

//         // 인가
//         http.exceptionHandling(config -> {
//             config.accessDeniedHandler(new CustomAccessDeniedHandler());
//         });

//         return http.build();
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         // 패스워드 암호화 (BCrypt 해싱 알고리즘)
//         return new BCryptPasswordEncoder();
//     }

//     // CORS 설정 활성화
//     @Bean
//     public CorsConfigurationSource corsConfigurationSource() {

//         CorsConfiguration configuration = new CorsConfiguration();
//         configuration.setAllowedOriginPatterns(Arrays.asList("*"));
//         configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//         configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
//         // configuration.setAllowCredentials(true);
//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 CORS 설정 적용

//         return source;
//     }
// }

// java config class
@Configuration
@EnableWebSecurity
public class CustomSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(httpSecurityCorsConfigurer -> {
            httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());
        });

        http.sessionManagement(sessionConfig -> {
            sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        http.csrf(csrf -> csrf.disable());

        http.formLogin(config -> {
            config.loginProcessingUrl("/api/v1/members/login");
            config.usernameParameter("email");
            config.successHandler(new ApiAuthenticationSuccessHandler());
            config.failureHandler(new ApiAuthenticationFailureHandler());
        });

        http.

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization","Cache-Control","Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}