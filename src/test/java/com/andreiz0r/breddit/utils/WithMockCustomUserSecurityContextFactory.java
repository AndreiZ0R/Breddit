package com.andreiz0r.breddit.utils;

import com.andreiz0r.breddit.model.User;
import com.andreiz0r.breddit.model.UserRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        User moderator = UserUtils.createRandomUser();
        moderator.setRole(UserRole.Mod);

        Authentication authentication = new UsernamePasswordAuthenticationToken(moderator.getUsername(), moderator.getPassword());
        context.setAuthentication(authentication);

        return context;
    }
}