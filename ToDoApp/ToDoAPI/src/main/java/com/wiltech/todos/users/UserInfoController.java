package com.wiltech.todos.users;

import static java.util.stream.Collectors.toList;

import static org.springframework.http.ResponseEntity.ok;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wiltech.todos.users.user.User;

/**
 * The type Userinfo controller.
 * This end point is to give details on current userr logged on.
 */
@RestController()
public class UserInfoController {

    /**
     * Current user response entity.
     * @param userDetails the user details
     * @return the response entity
     */
    @GetMapping("/me")
    public ResponseEntity currentUser(@AuthenticationPrincipal final UserDetails userDetails) {

        final User user = (User) userDetails;
        final Map<Object, Object> model = new HashMap<>();
        model.put("userId", ((User) userDetails).getId());
        model.put("userName", userDetails.getUsername());
        model.put("roles", userDetails.getAuthorities()
                .stream()
                .map(a -> ((GrantedAuthority) a).getAuthority())
                .collect(toList())
        );

        return ok(model);
    }
}
