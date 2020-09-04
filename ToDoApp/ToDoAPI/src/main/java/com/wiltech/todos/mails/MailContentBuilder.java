package com.wiltech.todos.mails;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import lombok.AllArgsConstructor;

/**
 * Service to crate and send emails.
 */
@Service
@AllArgsConstructor
public class MailContentBuilder {
    private final TemplateEngine templateEngine;

    /**
     * Method used to inject a message onto the mailTemplate.html file. This works by using Thymeleaf.
     * @param message The message to set.
     * @return The html filed with variables.
     */
    String build(final String message) {
        final Context context = new Context();
        context.setVariable("message", message);

        return this.templateEngine.process("mailTemplate", context);
    }

}
