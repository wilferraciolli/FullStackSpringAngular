package com.wiltech.todos.providers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProviderNotFoundException extends RuntimeException {

    public ProviderNotFoundException() {
    }

    public ProviderNotFoundException(final Long providerId) {
        super("Provider: " + providerId + " not found.");
    }

}
