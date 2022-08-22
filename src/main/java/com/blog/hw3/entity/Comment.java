package com.blog.hw3.entity;

import com.blog.hw3.dto.CommentDto;
import com.blog.hw3.utils.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
public class Comment extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;        // 댓글 고유 id

    @ManyToOne
    @Column (name = "POST_ID", nullable = false)
    private Post post;    // 댓글이 달린 게시글 id

    @Column (nullable = false)
    private String author;

    @Column (nullable = false)
    private String content;

    public Comment (CommentDto commentDto) {
        this.post = commentDto.getPost();
        this.author = commentDto.getAuthor();
        this.content = commentDto.getContent();
    }

    public void update (CommentDto commentDto) {
        this.post = commentDto.getPost();
        this.author = commentDto.getAuthor();
        this.content = commentDto.getContent();
    }
}
