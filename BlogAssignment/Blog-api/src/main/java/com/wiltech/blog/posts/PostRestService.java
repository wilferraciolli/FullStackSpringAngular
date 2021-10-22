package com.wiltech.blog.posts;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostRestService {

    private final PostApplicationService postApplicationService;

    @GetMapping("")
    public ResponseEntity<List<PostDTO>> getAllPosts() {

        final List<PostDTO> posts = postApplicationService.findAll();

        return ResponseEntity.ok(posts);
    }
}
