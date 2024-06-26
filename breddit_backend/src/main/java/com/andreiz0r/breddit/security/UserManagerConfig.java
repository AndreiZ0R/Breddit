package com.andreiz0r.breddit.security;

import com.andreiz0r.breddit.exception.ApiException;
import com.andreiz0r.breddit.repository.UserRepository;
import com.andreiz0r.breddit.utils.AppUtils.ReturnMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManagerConfig implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(ReturnMessages.usernameNotFound(username), HttpStatus.NOT_FOUND));
    }
}
