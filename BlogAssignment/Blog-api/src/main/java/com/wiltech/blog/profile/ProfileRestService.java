package com.wiltech.blog.profile;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileRestService {

    private final ProfileApplicationService profileApplicationService;

    @GetMapping("")
    public ResponseEntity<ProfileDTO> getProfile() {

        final ProfileDTO profile = profileApplicationService.getProfile(1000L);

        return ResponseEntity.ok(profile);
    }
}
