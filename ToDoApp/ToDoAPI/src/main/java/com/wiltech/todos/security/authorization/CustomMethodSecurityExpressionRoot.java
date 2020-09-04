package com.wiltech.todos.security.authorization;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import com.wiltech.todos.users.user.User;

/**
 * The type Is owner security expression root.
 */
public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;

    /**
     * Instantiates a new Is owner security expression root.
     * @param authentication the authentication
     */
    public CustomMethodSecurityExpressionRoot(final Authentication authentication) {
        super(authentication);
    }

    public boolean checkOwnerByUserId(final Long id) {
        final User user = (User) this.getPrincipal();
        return user.getId().equals(id);
        //            return user.getOrganization().getId().longValue() == OrganizationId.longValue();
    }

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }

    @Override
    public void setFilterObject(final Object obj) {
        this.filterObject = obj;
    }

    @Override
    public void setReturnObject(final Object obj) {
        this.returnObject = obj;
    }
}
