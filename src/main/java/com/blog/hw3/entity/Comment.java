package com.blog.hw3.entity;

import com.blog.hw3.dto.comment.CommentDto;
import com.blog.hw3.utils.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
public class Comment extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;        // 댓글 고유 id

    @ManyToOne
    @JoinColumn (nullable = false)
    private Member member;  // 댓글 작성자

    @Column (nullable = false)
    private Long postId;    // 댓글이 달린 게시글 id

    @Column (nullable = false)
    private String content;

    public Comment(CommentDto commentDto, Member member) {
        this.postId = commentDto.getPostId();
        this.content = commentDto.getContent();
        this.member = member;
    }

    public void update (CommentDto commentDto) {
        this.content = commentDto.getContent();
    }
}
