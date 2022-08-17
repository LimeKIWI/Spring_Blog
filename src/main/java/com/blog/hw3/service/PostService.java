package com.blog.hw3.service;

import com.blog.hw3.dto.PasswordDto;
import com.blog.hw3.dto.PostRequestDto;
import com.blog.hw3.entity.Password;
import com.blog.hw3.entity.Post;
import com.blog.hw3.repository.PasswordRepository;
import com.blog.hw3.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Setter
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PasswordRepository passwordRepository;

    //글업데이트
    @Transactional
    public Post update(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));
        Password pw = passwordRepository.findById(id).orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));
        post.update(requestDto);
        pw.update(requestDto);
        return post;
    }

    //글저장
    @Transactional
    public Post create(PostRequestDto requestDto) {
        Post post = new Post(requestDto);
        Password pw = new Password(requestDto);
        postRepository.save(post);
        passwordRepository.save(pw);
        return post;
    }

    //글삭제
    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
        passwordRepository.deleteById(id);
    }

    //글상세보기
    public Post getDetailPost(long id) {
        return postRepository.findById(id).orElseThrow(() -> new NullPointerException("해당 글이 존재하지 않습니다."));
    }

    //글목록조회
    public List<Post> getPosts() {
        return postRepository.findAllByOrderByCreateAtDesc();
    }

    //비밀번호 확인
    public Boolean chkPassword(Long id, PasswordDto requestDto) {
        Password pw = passwordRepository.findById(id).orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));
        return pw.getPassword().equals(requestDto.getPassword());
    }


}
