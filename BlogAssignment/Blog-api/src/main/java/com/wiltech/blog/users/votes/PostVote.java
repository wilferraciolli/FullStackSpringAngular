package com.wiltech.blog.users.votes;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_vote")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long postId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PostVoteType voteValue;

    public void updateVote(final PostVoteType voteValue) {

        this.voteValue = voteValue;
    }
}
