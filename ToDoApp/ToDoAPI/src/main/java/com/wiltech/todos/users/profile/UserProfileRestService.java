package com.wiltech.todos.users.profile;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type User profile rest service.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/userprofile")
public class UserProfileRestService {

    @Autowired
    private UserProfileAppService userProfileAppService;

    /**
     * Gets user profile.
     * @param userDetails the user details
     * @return the user profile
     */
    @GetMapping("")
    public ResponseEntity<UserProfileResource> getUserProfile(@AuthenticationPrincipal final UserDetails userDetails) {
        if (Objects.isNull(userDetails)) {

            return ResponseEntity.ok(UserProfileResource.builder().build());
        }

        final UserProfileResource response = userProfileAppService.getUserProfile(userDetails);

        return ResponseEntity.ok(response);
    }
}
