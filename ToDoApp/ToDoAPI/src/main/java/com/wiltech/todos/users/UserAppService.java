package com.wiltech.todos.users;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wiltech.EventPublisher;
import com.wiltech.todos.exceptions.EntityNotFoundException;
import com.wiltech.todos.mails.MailService;
import com.wiltech.todos.mails.NotificationEmail;
import com.wiltech.todos.users.events.UserCreatedEvent;
import com.wiltech.todos.users.user.User;
import com.wiltech.todos.users.user.UserRepository;

/**
 * The type User app service.
 */
@Service
public class UserAppService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsViewRepository userDetailsViewRepository;

    @Autowired
    private UserResourceAssembler assembler;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    private MailService mailService;

    /**
     * Find users list.
     * @return the list
     */
    public List<UserResource> findUsers() {

        return this.userDetailsViewRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create user resource.
     * @param userResourceCreate the user resource create
     * @return the user resource
     */
    public UserResource create(@Valid final UserResource userResourceCreate) {
        final User user = User.builder()
                .username(userResourceCreate.getUsername())
                .password(this.passwordEncoder.encode(userResourceCreate.getPassword()))
                .active(userResourceCreate.getActive())
                .roles(userResourceCreate.getRoleIds())
                .build();

        this.userRepository.save(user);

        this.publishUserCreatedEventWithPersonDetails(user.getId(), userResourceCreate);
        this.sendEmailVerification(user.getId(), user.getUsername());

        return this.transpose(user);
    }

    /**
     * Find by id user resource.
     * @param id the id
     * @return the user resource
     */
    public UserResource findById(final Long id) {

        final UserDetailsView user = this.userDetailsViewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("could not find user for given id"));

        return this.convertToDTO(user);
    }

    public boolean checkUsernameAvailability(final String username) {

        return Long.valueOf(0L).equals(this.userRepository.checkUsernameExists(username));
    }

    /**
     * Update user resource.
     * @param id the id
     * @param userResourcePayload the user resource payload
     * @return the user resource
     */
    public UserResource update(final Long id, final UserResource userResourcePayload) {
        final User user = this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("could not find user for given id"));

        user.updateUser(userResourcePayload.getUsername(), this.passwordEncoder.encode(userResourcePayload.getPassword()),
                userResourcePayload.getRoleIds());
        this.userRepository.save(user);

        return this.transpose(user);
    }

    /**
     * Delete user buy id.
     * @param id the id
     */
    public void deleteById(final Long id) {
        final User user = this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("could not find user for given id"));

        this.userRepository.delete(user);
    }

    private UserResource transpose(final User user) {
        final UserResource userResource = UserResource.builder()
                .id(user.getId())
                .username(user.getUsername())
                //                .email(user.getUsername())
                .password(user.getPassword())
                .active(user.getActive())
                .roleIds(user.getRoles())
                .build();

        return userResource;
    }

    private void publishUserCreatedEventWithPersonDetails(final Long userId, final UserResource userResourceCreated) {

        this.eventPublisher.publishEvent(new UserCreatedEvent(this, userId, userResourceCreated.getFirstName(), userResourceCreated.getLastName(),
                userResourceCreated.getUsername(), userResourceCreated.getDateOfBirth()));
    }

    private void sendEmailVerification(final Long userId, final String username) {

        this.mailService.sendEmail(new NotificationEmail("Please active your account", username,
                "Please click on the link below to activate your account "
                        + "http://localhost:5001/api/auth/accountverification/"
                        + userId));
    }

    private UserResource convertToDTO(final UserDetailsView userDetailsView) {

        return this.assembler.convertToDTO(userDetailsView, this.resolveUserRoles(userDetailsView));
    }

    private List<String> resolveUserRoles(final UserDetailsView userDetailsView) {

        return this.userDetailsService.loadUserByUsername(userDetailsView.getUsername()).getAuthorities()
                .stream()
                .map(role -> role.getAuthority())
                .collect(Collectors.toList());
    }
}
