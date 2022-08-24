package com.blog.hw3.entity;

import com.blog.hw3.dto.SigninRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Member {

    @GeneratedValue (strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column (nullable = false, unique = true)
    private String nickName;

    @Column (nullable = false)
    private String password;


    public Member(SigninRequestDto signinRequestDto) {
        this.nickName = signinRequestDto.getNickName();
        this.password = signinRequestDto.getPassword();
    }
}
