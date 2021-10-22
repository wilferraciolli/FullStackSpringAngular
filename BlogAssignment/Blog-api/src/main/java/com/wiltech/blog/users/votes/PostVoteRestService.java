package com.wiltech.blog.users.votes;

import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/posts/{id}/vote")
@RequiredArgsConstructor
public class PostVoteRestService {

    private final PostVoteApplicationService applicationService;

    @PutMapping("")
    public ResponseEntity<?> update(@PathVariable("id") final Long id, @RequestParam(name = "voteValue", required = true) final String voteValue) {
        //TODO replace by person logged on
        final Long userId = 1000L;

        final PostVoteType vote = resolveVoteValue(voteValue);

        applicationService.resolvePostVote(id, userId, vote);

        return noContent().build();
    }

    private PostVoteType resolveVoteValue(final String voteValue) {

        return PostVoteType.resolveValue(voteValue);
    }
}
