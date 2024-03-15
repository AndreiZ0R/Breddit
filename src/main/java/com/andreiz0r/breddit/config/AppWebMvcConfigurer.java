package com.andreiz0r.breddit.config;

import com.andreiz0r.breddit.interceptors.LoggingRequestsInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.andreiz0r.breddit.utils.AppUtils.API_DEFAULT_HOST;
import static com.andreiz0r.breddit.utils.AppUtils.VITE_DEFAULT_HOST;

@Component
@RequiredArgsConstructor
public class AppWebMvcConfigurer implements WebMvcConfigurer {

    private final LoggingRequestsInterceptor loggingRequestsInterceptor;

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins(API_DEFAULT_HOST, VITE_DEFAULT_HOST);
    }

    @Override
    public void addInterceptors(@NonNull final InterceptorRegistry registry) {
        registry.addInterceptor(loggingRequestsInterceptor);
    }
}
