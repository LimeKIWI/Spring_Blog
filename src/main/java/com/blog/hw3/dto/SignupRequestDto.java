package com.blog.hw3.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequestDto {
    private String nickName;
    private String password;
    private String passwordConfirm;

}
