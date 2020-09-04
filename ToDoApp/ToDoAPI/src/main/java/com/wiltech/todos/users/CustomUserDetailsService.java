package com.wiltech.todos.users;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.wiltech.todos.users.user.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository useRepository;

    public CustomUserDetailsService(final UserRepository useRepository) {
        this.useRepository = useRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return this.useRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
    }
}
