package com.blog.hw3.entity;

import com.blog.hw3.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
// 비밀번호만을 저장하기 위한 테이블
public class Password {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;    // 비밀번호 기본키

    // 비밀번호
    @Column(nullable = false)
    private String password;

    //컬럼생성
    public Password(PostRequestDto requestDto) {
        this.password = requestDto.getPassword();
    }

    //업데이트
    public void update(PostRequestDto requestDto) {
        this.password = requestDto.getPassword();
    }
}
