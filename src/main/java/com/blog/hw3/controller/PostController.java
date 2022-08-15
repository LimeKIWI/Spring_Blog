package com.blog.hw3.controller;

import com.blog.hw3.dto.PostRequestDto;
import com.blog.hw3.entity.Post;
import com.blog.hw3.repository.PostRepository;
import com.blog.hw3.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;

    // 글 작성
    @PostMapping("/api/posts")
    public Post createPost(@RequestBody PostRequestDto requestDto) {
        Post post = new Post(requestDto);
        return postRepository.save(post);
    }

    // 비밀번호 확인
    @PostMapping("/api/auth/${id}")
    public boolean isValidPassword(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        // postService.chkPassword(id, requestDto)
        return true;
    }

    // 글 수정
    @PatchMapping("/api/posts/${id}")
    public boolean updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        // postService.update(id, requestDto);
        return true;
    }

    // 글 삭제
    @DeleteMapping("/api/posts/${id}")
    public long deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
        return id;
    }

    // 전체 조회
    @GetMapping("/api/posts")
    public List<Post> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc();
    }

    // 글 조회
    @GetMapping("/api/posts/${id}")
    public Post getDetailPosts(@PathVariable Long id) {
        return postRepository.findById(id).orElseThrow(() -> new NullPointerException("해당 글이 존재하지 않습니다."));
    }
}
