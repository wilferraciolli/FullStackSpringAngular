package com.wiltech.blog.users.posts;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wiltech.blog.users.posts.likes.PostLike;
import com.wiltech.blog.users.posts.likes.PostLikeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostApplicationService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    public List<PostDTO> findAll() {

        return postRepository.findAll()
                .stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PostDTO> findAllForUser(final Long userId) {

        return postRepository.findAllByUserId(userId)
                .stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private PostDTO convertToDTO(final Post post) {

        final List<PostLike> postLikes = postLikeRepository.findPostLikeByPostId(post.getId());

        final PostDTO postDTO = PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .creationDateTime(post.getCreationDateTime())
                .tags(post.getTags().stream().map(PostTag::getValue).collect(Collectors.toList()))
                .likesCount(postLikes.size())
                .build();

        return postDTO;
    }
}
