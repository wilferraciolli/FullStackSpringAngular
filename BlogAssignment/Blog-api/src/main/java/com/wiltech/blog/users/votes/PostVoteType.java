package com.wiltech.blog.users.votes;

import org.apache.commons.lang3.EnumUtils;

/**
 * The enum Post vote type.
 */
public enum PostVoteType {

    /**
     * Up post vote type.
     */
    UP("up"),
    /**
     * Down post vote type.
     */
    DOWN("down");

    private String value;

    PostVoteType(final String value) {
    }

    public static PostVoteType resolveValue(final String valueToResolve) {

        if (EnumUtils.isValidEnum(PostVoteType.class, valueToResolve)) {
            return PostVoteType.valueOf(valueToResolve);
        }

        throw new IllegalArgumentException("Invalid value to resolve");
    }

    /**
     * Gets value.
     * @return the value
     */
    public String getValue() {
        return value;
    }
}
