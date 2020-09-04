/*
 * (c) Midland Software Limited 2019
 * Name     : EnumVlidator.java
 * Author   : ferraciolliw
 * Date     : 18 Sep 2019
 */
package com.wiltech.todos.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;

/**
 * The interface Valid enum. This annotation is used to validate any enums against their value attribute.
 */
@Documented
@Constraint(validatedBy = ValidEnumValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@NotNull(message = "Value cannot be null")
@ReportAsSingleViolation
public @interface ValidEnum {

    /**
     * Enum clazz class.
     * @return the class
     */
    Class<? extends Enum<?>> enumClazz();

    /**
     * Message string.
     * @return the string
     */
    String message() default "Enum Value is not valid";

    /**
     * Groups class [ ].
     * @return the class [ ]
     */
    Class<?>[] groups() default {};

    /**
     * Payload class [ ].
     * @return the class [ ]
     */
    Class<? extends Payload>[] payload() default {};
}
