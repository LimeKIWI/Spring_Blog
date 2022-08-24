package com.blog.hw3.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SigninRequestDto {
    private String nickName;
    private String password;
}
