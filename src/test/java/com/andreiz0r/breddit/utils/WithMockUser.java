package com.andreiz0r.breddit.utils;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockUser {
    String username() default "MOD_USER";

    String[] roles() default {"USER"};

    String password() default "mypass";
}
