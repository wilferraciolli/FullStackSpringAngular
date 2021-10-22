package com.wiltech.blog.users.votes;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostVoteRepository extends JpaRepository<PostVote, Long> {

    Optional<PostVote> findPostLikeByPostIdAndUserId(Long postId, Long userId);

    @Query("SELECT v FROM PostVote v WHERE v.postId = :postId AND v.voteValue = :postVoteType")
    List<PostVote> findPostsByPostIdAndVoteType(Long postId, PostVoteType postVoteType);
}
