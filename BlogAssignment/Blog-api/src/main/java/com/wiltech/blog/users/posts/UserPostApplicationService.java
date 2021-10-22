package com.wiltech.blog.users.posts;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wiltech.blog.posts.PostAssembler;
import com.wiltech.blog.posts.PostDTO;
import com.wiltech.blog.posts.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserPostApplicationService {

    private final PostRepository postRepository;
    private final PostAssembler postAssembler;

    public List<PostDTO> findAllForUser(final Long userId) {
        return postRepository.findAllByUserId(userId).stream()
                .map(postAssembler::convertToDTO)
                .collect(Collectors.toList());
    }

}
