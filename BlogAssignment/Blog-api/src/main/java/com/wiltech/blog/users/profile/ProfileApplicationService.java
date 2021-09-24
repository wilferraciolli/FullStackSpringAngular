package com.wiltech.blog.users.profile;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wiltech.blog.core.Link;

@Service
public class ProfileApplicationService {

    public ProfileDTO getProfile(final Long id) {

        final Profile profile = new Profile(id);

        final ProfileDTO dto = ProfileDTO.builder()
                .id(profile.getId())
                .links(generateLinks(id))
                .build();

        return dto;
    }

    private List<Link> generateLinks(final Long id) {
        return List.of(
                Link.builder().name("posts").href("users/" + id + "/posts").build()
        );
    }
}
