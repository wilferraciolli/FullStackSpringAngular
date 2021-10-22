package com.wiltech.blog.users.votes;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostVoteApplicationService {

    private final PostVoteRepository postVoteRepository;

    public void resolvePostVote(final Long postId, final Long userId, final PostVoteType voteValue) {

        postVoteRepository.findPostLikeByPostIdAndUserId(postId, userId)
                .ifPresentOrElse(
                        p -> this.updatePost(p, voteValue),
                        () -> this.createPost(postId, userId, voteValue)
                );
    }

    private void createPost(final Long postId, final Long userId, final PostVoteType voteValue) {
        postVoteRepository.save(PostVote.builder()
                .postId(postId)
                .userId(userId)
                .voteValue(voteValue)
                .build());
    }

    private void updatePost(final PostVote postVote, final PostVoteType voteValue) {
        // remove post vote if same value is passed on
        if (postVote.getVoteValue().equals(voteValue)) {
            postVoteRepository.delete(postVote);

        } else {
            postVote.updateVote(voteValue);
            postVoteRepository.save(postVote);
        }
    }

}
