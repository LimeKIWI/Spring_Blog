package com.blog.hw3.dto.comment;

import com.blog.hw3.entity.Member;
import lombok.Getter;


// 댓글 정보를 가져오는 DTO
@Getter
public class CommentDto {
    private Member member;
    private String content;
    private Long postId;
}
