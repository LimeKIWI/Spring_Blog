package com.blog.hw3.service;

import com.blog.hw3.dto.PasswordDto;
import com.blog.hw3.dto.PostRequestDto;
import com.blog.hw3.entity.Post;
import com.blog.hw3.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Setter
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    //글업데이트
    @Transactional
    public Long update(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));
        post.update(requestDto);
        return post.getId();
    }

    //비밀번호 확인
    public boolean chkPassword(Long id, PasswordDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));
        return post.getPassword().equals(requestDto.getPassword());
    }

}
