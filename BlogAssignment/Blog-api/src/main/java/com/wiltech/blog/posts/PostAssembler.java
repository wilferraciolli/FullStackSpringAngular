package com.wiltech.blog.posts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wiltech.blog.core.Link;
import com.wiltech.blog.users.votes.PostVoteRepository;
import com.wiltech.blog.users.votes.PostVoteType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostAssembler {

    private final PostVoteRepository postVoteRepository;

    public PostDTO convertToDTO(final Post post) {

        final PostDTO postDTO = PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .creationDateTime(post.getCreationDateTime())
                .tags(post.getTags().stream().map(PostTag::getValue).collect(Collectors.toList()))
                .likesCount(getPostLikeCount(post.getId()))
                .links(generateLinks(post.getId()))
                .build();

        return postDTO;
    }

    private List<Link> generateLinks(final Long postId) {
        final List<Link> links = new ArrayList<>();

        //TODO replace by person logged on
        final Long userId = 1000L;

        postVoteRepository.findPostLikeByPostIdAndUserId(postId, userId)
                .ifPresentOrElse(
                        postVote -> links.add(resolvePostVoteLink(postId, postVote.getVoteValue())),
                        () -> links.addAll(generateBothVoteLinks(postId))
                );

        return links;
    }

    private List<Link> generateBothVoteLinks(final Long postId) {

        return List.of(
                buildVotePost(postId, "likePost", PostVoteType.UP),
                buildVotePost(postId, "dislikePost", PostVoteType.DOWN)
        );
    }

    private Link resolvePostVoteLink(final Long postId, final PostVoteType postVoteType) {
        if (postVoteType.equals(PostVoteType.UP)) {
            return buildVotePost(postId, "dislikePost", PostVoteType.DOWN);
        } else {
            return buildVotePost(postId, "likePost", PostVoteType.UP);
        }
    }

    private Link buildVotePost(final Long postId, final String likePost, final PostVoteType postVoteType) {
        return Link.builder().name(likePost).href("posts/" + postId + "/vote" + "?voteValue=" + postVoteType.name()).build();
    }

    private int getPostLikeCount(final Long postId) {
        return postVoteRepository.findPostsByPostIdAndVoteType(postId, PostVoteType.UP).size();
    }

}
