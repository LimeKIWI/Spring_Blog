package com.blog.hw3.dto;

import com.blog.hw3.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

// 댓글 정보를 가져오는 DTO
@Getter
public class CommentDto {
    private Long id;
    private Post post;
    private String author;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
}
