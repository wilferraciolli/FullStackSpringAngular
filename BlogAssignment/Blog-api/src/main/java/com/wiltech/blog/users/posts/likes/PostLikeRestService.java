package com.wiltech.blog.users.posts.likes;

import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/posts/{id}/likes")
@RequiredArgsConstructor
public class PostLikeRestService {

    private final PostLikeApplicationService applicationService;

    @PutMapping("")
    public ResponseEntity<?> update(@PathVariable("id") final Long id) {
        //TODO replace by person logged on
        final Long userId = 1000L;

        applicationService.resolveLike(id, userId);

        return noContent().build();
    }
}
