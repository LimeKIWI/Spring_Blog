package com.blog.hw3.controller;

import com.blog.hw3.dto.post.DetailPostDto;
import com.blog.hw3.dto.post.PostListDto;
import com.blog.hw3.dto.post.PostRequestDto;
import com.blog.hw3.security.UserDetailImp;
import com.blog.hw3.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    // 글 작성
    @PostMapping("/api/auth/posts")
    public DetailPostDto createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailImp userDetail)  {
        return postService.create(requestDto, userDetail.getUsername());
    }

    // 글 수정
    @PutMapping("/api/auth/posts/{id}")
    public DetailPostDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailImp userDetail)  {
        return postService.update(id, requestDto, userDetail.getUsername());
    }

    // 글 삭제
    @DeleteMapping("/api/auth/posts/{id}")
    public Long deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailImp userDetail) {
        postService.delete(id, userDetail.getUsername());
        return id;
    }

    // 전체 조회
    @GetMapping("/api/posts")
    public List<PostListDto> getPosts() {
        return postService.getPosts();
    }

    // 글 조회
    @GetMapping("/api/posts/{id}")
    public DetailPostDto getDetailPosts(@PathVariable Long id) {
        return postService.getDetailPost(id);
    }
}