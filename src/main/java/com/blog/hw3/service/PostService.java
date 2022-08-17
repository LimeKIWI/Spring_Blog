package com.blog.hw3.service;

import com.blog.hw3.dto.DetailPostDto;
import com.blog.hw3.dto.PasswordDto;
import com.blog.hw3.dto.PostListDto;
import com.blog.hw3.dto.PostRequestDto;
import com.blog.hw3.entity.Post;
import com.blog.hw3.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Setter
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    //글업데이트
    @Transactional
    public DetailPostDto update(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));
        post.update(requestDto);
        return new DetailPostDto(post);

    }

    //글저장
    @Transactional
    public DetailPostDto create(PostRequestDto requestDto) {
        Post post = new Post(requestDto);
        postRepository.save(post);
        return new DetailPostDto(post);
    }

    //글삭제
    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    //글상세보기
    public DetailPostDto getDetailPost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NullPointerException("해당 글이 존재하지 않습니다."));
        return new DetailPostDto(post);
    }

    //글목록보기
    public List<PostListDto> getPosts() {
        List<Post> list = postRepository.findAllByOrderByCreateAtDesc();
        List<PostListDto> plist = new ArrayList<>();
        for(Post post : list){
            PostListDto listDto = PostListDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .author(post.getAuthor())
                    .createAt(post.getCreateAt())
                    .modifiedAt(post.getModifiedAt())
                    .build();
            plist.add(listDto);
        }
        return plist;
    }

    //비밀번호 확인
    public Boolean chkPassword(Long id, PasswordDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));
        return post.getPassword().equals(requestDto.getPassword());
    }

}