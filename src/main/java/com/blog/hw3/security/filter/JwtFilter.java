package com.blog.hw3.security.filter;

import com.blog.hw3.security.provider.JwtProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    public JwtFilter(JwtProvider jwtProvider)  {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. Request Header 에서 토큰을 꺼냄
        String jwt = jwtProvider.resolveToken(request);
        String rtk = (request).getHeader("Access-Control-Request-Headers");    // 임의의 헤더에 refresh토큰을 담아서 보내옴

        if(jwt != null && rtk != null) {
            if (jwtProvider.validateToken(jwt) && jwtProvider.validateToken(rtk)) {   // token들 검증
                Authentication auth = jwtProvider.getAuthentication(jwt);    // 인증 객체 생성
                jwtProvider.validateRefreshToken(rtk, auth.getName());       // refresh토큰의 내용이 db의 값과 일치하는지 확인
                SecurityContextHolder.getContext().setAuthentication(auth); // access토큰과 refresh토큰이 모두 정상이라면 SecurityContextHolder에 인증 객체 저장
            }
        }
        filterChain.doFilter(request, response);
    }
}
