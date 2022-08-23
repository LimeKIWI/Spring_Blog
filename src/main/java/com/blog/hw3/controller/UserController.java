package com.blog.hw3.controller;

import com.blog.hw3.dto.SignupRequestDto;
import com.blog.hw3.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("api/member/signup")
    public String signup(@RequestBody SignupRequestDto signupRequestDto) throws IllegalAccessException {
        return userService.registerUser(signupRequestDto);
    }
}
