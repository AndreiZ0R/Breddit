package com.andreiz0r.breddit.security;

import com.andreiz0r.breddit.controller.AbstractController;
import com.andreiz0r.breddit.utils.AppUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @SneakyThrows
    @Override
    protected void doFilterInternal(
            final @NonNull HttpServletRequest request,
            final @NonNull HttpServletResponse response,
            final @NonNull FilterChain filterChain) {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!isValidHeader(authHeader)) {
            handleInvalidAuth(response, AppUtils.BAD_TOKEN, HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String jwtToken = getJwtFromHeader(authHeader);
        Optional<String> usernameOptional = jwtService.extractUsername(jwtToken);
        if (usernameOptional.isEmpty()) {
            handleInvalidAuth(response, AppUtils.BAD_TOKEN, HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String username = usernameOptional.get();
        try {
            UserDetails user = userDetailsService.loadUserByUsername(username);
            if (!isAuthenticated(username) && jwtService.isValidToken(jwtToken, user)) {
                authenticate(user, request);
            }
        } catch (Exception e) {
            handleInvalidAuth(response, e.getMessage(), HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void authenticate(final UserDetails details, final HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    @SneakyThrows
    private void handleInvalidAuth(final HttpServletResponse response, final String message, final int status) {
        AbstractController.setServletResponse(response, message, status);
        //TODO: check those
//        response.setContentType("text/html");
//        response.sendRedirect("/login");
    }

    private boolean isValidHeader(final String header) {
        return header != null && header.startsWith(AppUtils.JWT_START_STRING);
    }

    private String getJwtFromHeader(final String header) {
        return header.substring(7);
    }

    private boolean isAuthenticated(final String username) {
        return username != null && SecurityContextHolder.getContext().getAuthentication() != null;
    }

    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) {
        String path = request.getRequestURI();
        return AppUtils.JWT_FILTER_WHITELISTED_URLS.stream()
                .anyMatch(path::contains);
    }
}