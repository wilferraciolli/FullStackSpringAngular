/*
 * (c) Midland Software Limited 2019
 * Name     : EnumValidatorImpl.java
 * Author   : ferraciolliw
 * Date     : 18 Sep 2019
 */
package com.wiltech.todos.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The type Valid enum validator.
 */
public class ValidEnumValidator implements ConstraintValidator<ValidEnum, String> {

    /**
     * The Value list.
     */
    List<String> valueList = new ArrayList<>();

    @Override public void initialize(final ValidEnum constraintAnnotation) {
        this.valueList = new ArrayList<String>();
        final Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClazz();

        @SuppressWarnings("rawtypes") final Enum[] enumValArr = enumClass.getEnumConstants();

        for (@SuppressWarnings("rawtypes") final
        Enum enumVal : enumValArr) {
            this.valueList.add(enumVal.toString().toUpperCase());
        }
    }

    @Override public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (!this.valueList.contains(value.toUpperCase())) {
            return false;
        }
        return true;
    }
}
