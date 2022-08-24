package com.blog.hw3.service;

import com.blog.hw3.dto.post.DetailPostDto;
import com.blog.hw3.dto.post.PostListDto;
import com.blog.hw3.dto.post.PostRequestDto;
import com.blog.hw3.entity.Comment;
import com.blog.hw3.entity.Member;
import com.blog.hw3.entity.Post;
import com.blog.hw3.repository.CommentRepository;
import com.blog.hw3.repository.MemberRepository;
import com.blog.hw3.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    

    private Member getMember(String nickName) throws IllegalAccessException {
        Optional<Member> mem = memberRepository.findByNickName(nickName);
        if(!mem.isPresent())
            throw new IllegalAccessException("사용자 정보가 없습니다!");
        return mem.get();
    }

    //글업데이트
    @Transactional
    public DetailPostDto update(Long id, PostRequestDto requestDto, String nickName) throws IllegalAccessException {
        Member member = getMember(nickName);
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalAccessException("해당 글이 존재하지 않습니다."));
        if(!post.getMember().getNickName().equals(member.getNickName()))
            throw new IllegalAccessException("작성자만 수정할 수 있습니다.");
        post.update(requestDto);
        return new DetailPostDto(post);

    }

    //글저장
    @Transactional
    public DetailPostDto create(PostRequestDto requestDto, String nickName) throws IllegalAccessException {
        Member member = getMember(nickName);
        Post post = new Post(requestDto, member);
        postRepository.save(post);
        return new DetailPostDto(post);
    }

    //글삭제
    @Transactional
    public void delete(Long id, String nickName) throws IllegalAccessException {
        Member member = getMember(nickName);
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalAccessException("해당 글이 존재하지 않습니다."));
        if(!post.getMember().getNickName().equals(member.getNickName()))
            throw new IllegalAccessException("작성자만 삭제할 수 있습니다.");
        postRepository.deleteById(id);
        List<Comment> list = commentRepository.findAllByPostId(id);
        for(Comment comment : list) {
            commentRepository.deleteById(comment.getId());
        }
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
                    .author(post.getMember().getNickName())
                    .createAt(post.getCreateAt())
                    .modifiedAt(post.getModifiedAt())
                    .build();
            plist.add(listDto);
        }
        return plist;
    }
    
}