package com.blog.hw3;

import com.blog.hw3.dto.PostRequestDto;
import com.blog.hw3.entity.Post;
import com.blog.hw3.repository.PostRepository;
import com.blog.hw3.service.PostService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@EnableJpaAuditing // 시간 자동 변경이 가능하도록 합니다.
@SpringBootApplication // 스프링 부트임을 선언합니다.
public class Hw3Application {
	public static void main(String[] args) {
		SpringApplication.run(Hw3Application.class, args);
	}
	@Bean
	public CommandLineRunner demo(PostRepository postRepository, PostService postService) {
		return (args) -> {
			postRepository.save(new Post("제목","이름","내용","비밀번호"));

			System.out.println("데이터 인쇄");
			List<Post> postList = postRepository.findAll();
			for (Post post : postList) {
				System.out.println(post.getId());
				System.out.println(post.getTitle());
				System.out.println(post.getAuthor());
				System.out.println(post.getContent());
				System.out.println(post.getPassword());
			}

			PostRequestDto requestDto = new PostRequestDto("제목2","이름2","내용2","비밀번호2");
			postService.update(1L, requestDto);
			postList = postRepository.findAll();
			for (Post post : postList) {
				System.out.println(post.getId());
				System.out.println(post.getTitle());
				System.out.println(post.getAuthor());
				System.out.println(post.getContent());
				System.out.println(post.getPassword());
			}

			postRepository.deleteAll();
		};
	}
}
