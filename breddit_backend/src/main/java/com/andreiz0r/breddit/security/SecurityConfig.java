package com.andreiz0r.breddit.security;

import com.andreiz0r.breddit.controller.AbstractController;
import com.andreiz0r.breddit.entity.UserRole;
import com.andreiz0r.breddit.utils.AppUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AppUtils.WHITE_LIST_URLS).permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/comments/**").hasAuthority(UserRole.Mod.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/posts/**").hasAuthority(UserRole.Mod.name())
                        .anyRequest().authenticated()
                )
                .exceptionHandling(handler -> handler
                        .accessDeniedHandler(((request, response, accessDeniedException) ->
                                AbstractController.setServletResponse(response, AppUtils.ACCESS_DENIED, HttpServletResponse.SC_FORBIDDEN)))
                )
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
