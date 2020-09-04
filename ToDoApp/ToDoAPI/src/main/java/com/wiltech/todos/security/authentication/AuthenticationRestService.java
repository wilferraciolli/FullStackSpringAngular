package com.wiltech.todos.security.authentication;

import static org.springframework.http.HttpStatus.OK;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wiltech.BaseRestService;
import com.wiltech.todos.security.jwt.refresh.RefreshTokenException;
import com.wiltech.todos.security.jwt.refresh.RefreshTokenRequest;

/**
 * The type Auth controller.
 */
@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthenticationRestService extends BaseRestService {

    @Autowired
    private AuthenticationService authenticateService;

    /**
     * Signin response entity.
     * @param data the data
     * @return the response entity
     */
    @PostMapping("/signin")
    public ResponseEntity signIn(@RequestBody @Valid final AuthenticationRequest data) {
        final AuthenticationResourceResponse authenticationDetails = this.authenticateService.authenticate(data);

        return ResponseEntity.status(OK).body(authenticationDetails);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid final RegistrationRequest data) {
        this.authenticateService.register(data);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/accountverification/{userId}")
    public ResponseEntity verifyAccount(@PathVariable("userId") final Long userId) {
        this.authenticateService.verifyAccount(userId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh/token")
    public ResponseEntity register(@RequestBody @Valid final RefreshTokenRequest data) throws RefreshTokenException {
        final AuthenticationResourceResponse authenticationDetails = this.authenticateService.refreshToken(data);

        return ResponseEntity.status(OK).body(authenticationDetails);
    }

}
