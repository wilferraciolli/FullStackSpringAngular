package com.wiltech.todos.security.authorization;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * The type Custom permission evaluator. This is to allow custom permission hasPermission for methods.
 */
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(final Authentication auth, final Object targetDomainObject, final Object permission) {
        if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)) {
            return false;
        }
        final String targetType = targetDomainObject.getClass().getSimpleName().toUpperCase();

        return hasPrivilege(auth, targetType, permission.toString().toUpperCase());
    }

    @Override
    public boolean hasPermission(final Authentication auth, final Serializable targetId, final String targetType, final Object permission) {
        if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        }

        return hasPrivilege(auth, targetType.toUpperCase(), permission.toString().toUpperCase());
    }

    private boolean hasPrivilege(final Authentication auth, final String targetType, final String permission) {
        for (final GrantedAuthority grantedAuth : auth.getAuthorities()) {
            System.out.println("here " + grantedAuth);
            if (grantedAuth.getAuthority().startsWith(targetType)) {
                if (grantedAuth.getAuthority().contains(permission)) {

                    return true;
                }
            }
        }

        return false;
    }
}
