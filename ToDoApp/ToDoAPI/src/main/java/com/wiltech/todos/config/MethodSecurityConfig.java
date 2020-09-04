package com.wiltech.todos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import com.wiltech.todos.security.authorization.CustomMethodSecurityExpressionHandler;
import com.wiltech.todos.security.authorization.CustomPermissionEvaluator;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Autowired
    private ApplicationContext context;

    //    @Override
    //    protected MethodSecurityExpressionHandler createExpressionHandler() {
    //        final DefaultMethodSecurityExpressionHandler result = new DefaultMethodSecurityExpressionHandler();
    //        result.setApplicationContext(this.context);
    //        result.setParameterNameDiscoverer(new AnnotationParameterNameDiscoverer(PathVariable.class.getName()));
    //        return result;
    //    }

    //
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        final CustomMethodSecurityExpressionHandler expressionHandler =
                new CustomMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(new CustomPermissionEvaluator());
        return expressionHandler;
    }
}
