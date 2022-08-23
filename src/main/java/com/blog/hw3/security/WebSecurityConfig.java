package com.blog.hw3.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
public class WebSecurityConfig {

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
        return (web) -> web.ignoring()
                .antMatchers("/h2-console/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 회원 관리 처리 API (POST /user/**) 에 대해 CSRF 무시
        http.csrf()
                .ignoringAntMatchers("/api/member/**");

        http
                .authorizeHttpRequests((authz) -> authz
                        // 회원 관리 처리 API 전부를 login 없이 허용
                        .antMatchers("/api/member/**").permitAll()
                        // 어떤 요청이든 '인증'
                        .anyRequest().authenticated()
                )
                // [로그인 기능]
                .formLogin()
                // 로그인 View 제공 (GET /user/login)
                //.loginPage("/api/member/login")
                // 로그인 처리 (POST /user/login)
                .loginProcessingUrl("/api/member/login")
                // 로그인 처리 후 성공 시 URL
                .defaultSuccessUrl("/hello.html")
                // 로그인 처리 후 실패 시 URL
                .failureUrl("/api/member/login?error")
                .permitAll()
                .and()
                // [로그아웃 기능]
                .logout()
                // 로그아웃 처리 URL
                .logoutUrl("/api/member/logout")
                .permitAll();

        return http.build();
    }
}