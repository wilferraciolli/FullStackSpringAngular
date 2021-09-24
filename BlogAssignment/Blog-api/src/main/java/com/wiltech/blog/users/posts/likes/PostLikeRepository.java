package com.wiltech.blog.users.posts.likes;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findPostLikeByPostIdAndUserId(Long postId, Long userId);

    List<PostLike> findPostLikeByPostId(Long postId);
}
