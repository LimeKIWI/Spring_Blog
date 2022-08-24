package com.blog.hw3.controller;

import com.blog.hw3.dto.SigninRequestDto;
import com.blog.hw3.dto.SignupRequestDto;
import com.blog.hw3.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("api/member/signup")
    public String signup(@RequestBody @Valid SignupRequestDto signupRequestDto) throws IllegalAccessException {
        return memberService.registerUser(signupRequestDto);
    }

    // 로그인
    @PostMapping("api/member/login")
    public String login(@RequestBody SigninRequestDto signinRequestDto, HttpServletResponse response) {
        return memberService.login(signinRequestDto, response);
    }

}
