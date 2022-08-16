package com.blog.hw3.service;

import com.blog.hw3.dto.PasswordDto;
import com.blog.hw3.dto.PostRequestDto;
import com.blog.hw3.entity.Post;
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

    //글업데이트
    @Transactional
    public Long update(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));
        post.update(requestDto);
        return post.getId();
    }

    @Transactional
    public Post create(PostRequestDto requestDto) {
        Post post = new Post(requestDto);
        return postRepository.save(post);
    }

    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public Post getDetailPost(long id) {
        return postRepository.findById(id).orElseThrow(() -> new NullPointerException("해당 글이 존재하지 않습니다."));
    }

    public List<Post> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc();
    }

    //비밀번호 확인
    public boolean chkPassword(Long id, PasswordDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));
        return post.getPassword().equals(requestDto.getPassword());
    }

}
