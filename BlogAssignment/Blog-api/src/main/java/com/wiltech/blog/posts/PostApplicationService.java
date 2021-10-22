package com.wiltech.blog.posts;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostApplicationService {

    private final PostRepository postRepository;
    private final PostAssembler postAssembler;

    public List<PostDTO> findAll() {

        return postRepository.findAll().stream()
                .map(postAssembler::convertToDTO)
                .collect(Collectors.toList());
    }
}
