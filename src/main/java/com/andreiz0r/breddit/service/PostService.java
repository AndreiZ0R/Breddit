package com.andreiz0r.breddit.service;

import com.andreiz0r.breddit.dto.DTOMapper;
import com.andreiz0r.breddit.dto.PostDTO;
import com.andreiz0r.breddit.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public List<PostDTO> findAll() {
        return postRepository.findAll().stream().map(DTOMapper::mapPostToDTO).collect(Collectors.toList());
    }

    public Optional<PostDTO> findById(final Integer id) {
        return postRepository.findById(id).map(DTOMapper::mapPostToDTO);
    }

    public Optional<PostDTO> deleteById(final Integer id) {
        return postRepository.deleteByIdAndReturn(id).map(DTOMapper::mapPostToDTO);
    }
}
