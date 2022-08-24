package com.blog.hw3.service;

import com.blog.hw3.dto.comment.CommentDto;
import com.blog.hw3.dto.comment.CommentResponseDto;
import com.blog.hw3.entity.Comment;
import com.blog.hw3.entity.Member;
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
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    private Member getMember(String nickName) {
        Optional<Member> mem = memberRepository.findByNickName(nickName);
        if(!mem.isPresent())
            throw new IllegalArgumentException("사용자 정보가 없습니다!");
        return mem.get();
    }

    private void extracted(CommentDto commentDto) {
        postRepository.findById(commentDto.getPostId()).orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다."));
    }

    // 댓글 생성
    @Transactional
    public CommentResponseDto createComment(CommentDto commentDto, String nickName)  {
        Member member = getMember(nickName);
        extracted(commentDto);
        Comment comment = new Comment(commentDto, member);
        commentRepository.save(comment);
        return CommentResponseDto.builder()
                .id(comment.getId())
                .author((comment.getMember().getNickName()))
                .content(comment.getContent())
                .createAt(comment.getCreateAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long id, CommentDto commentDto, String nickName) {
        Member member = getMember(nickName);
        extracted(commentDto);
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        if(!member.getNickName().equals(comment.getMember().getNickName()))
            throw new IllegalArgumentException("댓글 작성자가 다릅니다.");
        comment.update(commentDto);
        return CommentResponseDto.builder()
                .id(comment.getId())
                .author((comment.getMember().getNickName()))
                .content(comment.getContent())
                .createAt(comment.getCreateAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }


    // 댓글 삭제
    public Long deleteComment(Long id, String nickName)  {
        Member member = getMember(nickName);
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        if(!member.getNickName().equals(comment.getMember().getNickName()))
            throw new IllegalArgumentException("댓글 작성자가 다릅니다.");
        commentRepository.deleteById(id);
        return id;
    }

    // 댓글 목록 조회
    public List<CommentResponseDto> getCommentList(Long id) {
        List<Comment> list = commentRepository.findAllByPostId(id);
        List<CommentResponseDto> clist = new ArrayList<>();
            for (Comment c : list) {
                    CommentResponseDto commentDto = CommentResponseDto.builder()
                            .id(c.getId())
                            .author(c.getMember().getNickName())
                            .content(c.getContent())
                            .createAt(c.getCreateAt())
                            .modifiedAt(c.getModifiedAt())
                            .build();
                    clist.add(commentDto);
            }

        return clist;
    }
}
