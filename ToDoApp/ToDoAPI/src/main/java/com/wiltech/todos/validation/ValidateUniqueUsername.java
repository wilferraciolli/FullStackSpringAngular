package com.wiltech.todos.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = {ValidateUniqueUsernameValidator.class})
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface ValidateUniqueUsername {

    /**
     * Message.
     * @return message.
     */
    String message() default "{Users.username.NonUnique}";

    /**
     * Allow blanks.
     * @return status of the Response.
     */
    boolean allowBlanks() default true;

    /**
     * Groups.
     * @return an array of Class.
     */
    Class<?>[] groups() default {};

    /**
     * Payload.
     * @return an array of Payloads.
     */
    Class<? extends Payload>[] payload() default {};
}
