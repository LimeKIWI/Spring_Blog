package com.blog.hw3.entity;

import com.blog.hw3.dto.post.PostRequestDto;
import com.blog.hw3.utils.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity                         // 엔티티(테이블임을 선언)
@NoArgsConstructor              // 기본생성자 생성생략
@Getter                         // getter함수 생성생략
public class Post extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;            // 게시물 고유번호

    @Column(nullable = false)   // not Null
    private String title;       // 게시물 이름

    @Column(nullable = false)
    private String content;     // 게시물 내용

    @ManyToOne
    @JoinColumn (nullable = false)
    private Member member;

    public Post(PostRequestDto requestDto, Member member) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.member = member;
    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

}