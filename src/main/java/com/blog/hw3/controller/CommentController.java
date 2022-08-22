package com.blog.hw3.controller;

import com.blog.hw3.dto.CommentDto;
import com.blog.hw3.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    //댓글 쓰기
    @PostMapping("api/comment")
    public void createComment() {
    }

    //댓글 수정
    @PutMapping("api/comment/{id}")
    public void updateComment() {

    }

    //댓글 삭제
    @DeleteMapping("api/comment/{id}")
    public void deleteComment() {

    }

    //댓글 목록 조회
    @GetMapping("api/comment/{id}")
    public void getComment() {

    }
}
