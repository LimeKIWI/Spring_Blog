package com.blog.hw3.dto;


import com.blog.hw3.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
// 글상세보기를 위한 Dto
public class DetailPostDto {
    private Long id;
    private String title;
    private String author;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public DetailPostDto (Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.author = post.getAuthor();
        this.content = post.getContent();
        this.createAt = post.getCreateAt();
        this.modifiedAt = post.getModifiedAt();
    }
}


