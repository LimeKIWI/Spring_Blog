package com.blog.hw3.dto;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

// 댓글 목록만을 가져오는 DTO
@Getter
@Builder
public class CommentListDto {
    private Long id;
    private String author;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
}
