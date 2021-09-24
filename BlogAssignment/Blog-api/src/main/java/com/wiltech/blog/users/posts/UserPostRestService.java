package com.wiltech.blog.users.posts;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/users/{userId}/posts")
@RequiredArgsConstructor
public class UserPostRestService {

    private final PostApplicationService postApplicationService;

    @GetMapping("")
    public ResponseEntity<List<PostDTO>> getAllPostsForUser(@PathVariable("userId") final Long userId) {

        final List<PostDTO> posts = postApplicationService.findAllForUser(userId);

        return ResponseEntity.ok(posts);
    }
}
