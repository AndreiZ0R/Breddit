package com.andreiz0r.breddit.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@Slf4j
public class LoggingRequestsInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull final HttpServletRequest request, @NonNull final HttpServletResponse response, @NonNull final Object handler) {
        log.info("[INCOMING {}]: session={}, path={}", request.getMethod(), request.getRequestURI(), request.getSession());
        response.setHeader("My-Header", "my-value");
        return true;
    }

    @Override
    public void postHandle(@NonNull final HttpServletRequest request, @NonNull final HttpServletResponse response, @NonNull final Object handler, final ModelAndView modelAndView) {
        log.info("[OUT] status={} for path={}", response.getStatus(), request.getRequestURI());
    }
}
