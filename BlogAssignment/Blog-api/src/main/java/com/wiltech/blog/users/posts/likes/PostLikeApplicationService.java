package com.wiltech.blog.users.posts.likes;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostLikeApplicationService {

    private final PostLikeRepository postLikeRepository;

    public void resolveLike(final Long postId, final Long userId) {

        postLikeRepository.findPostLikeByPostIdAndUserId(postId, userId)
                .ifPresentOrElse(
                        p -> postLikeRepository.delete(p),
                        () -> postLikeRepository.save(createPost(postId, userId))
                );
    }

    private PostLike createPost(final Long postId, final Long userId) {
        return PostLike.builder()
                .postId(postId)
                .userId(userId)
                .build();
    }
}
