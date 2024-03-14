package com.andreiz0r.breddit.service;

import com.andreiz0r.breddit.repository.SubthreadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubthreadService {
    private final SubthreadRepository subthreadRepository;
}
