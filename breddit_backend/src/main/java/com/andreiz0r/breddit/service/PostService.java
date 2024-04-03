package com.andreiz0r.breddit.service;

import com.andreiz0r.breddit.controller.message.CreatePostRequest;
import com.andreiz0r.breddit.controller.message.UpdatePostRequest;
import com.andreiz0r.breddit.dto.DTOMapper;
import com.andreiz0r.breddit.dto.PostDTO;
import com.andreiz0r.breddit.model.Post;
import com.andreiz0r.breddit.repository.PostRepository;
import com.andreiz0r.breddit.repository.UserRepository;
import com.andreiz0r.breddit.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<PostDTO> findAll() {
        return postRepository.findAll().stream().map(DTOMapper::mapPostToDTO).collect(Collectors.toList());
    }

    public Optional<PostDTO> findById(final Integer id) {
        return postRepository.findById(id).map(DTOMapper::mapPostToDTO);
    }

    public Optional<PostDTO> store(final CreatePostRequest request) {
        return userRepository.findById(request.getAuthorId())
                .map(author ->
                             DTOMapper.mapPostToDTO(
                                     postRepository.save(
                                             Post.builder()
                                                     .title(request.getTitle())
                                                     .body(request.getBody())
                                                     .author(author)
                                                     .postedAt(AppUtils.timestampNow())
                                                     .subthreadId(request.getSubthreadId())
                                                     .comments(List.of())
                                                     .votes(0)
                                                     .build()
                                     )
                             ));
    }

    public Optional<PostDTO> update(final Integer id, final UpdatePostRequest request) {
        return postRepository.findById(id)
                .map(post -> {
                    DTOMapper.updateValues(post, request);
                    postRepository.save(post);
                    return DTOMapper.mapPostToDTO(post);
                });
    }

    public Optional<PostDTO> deleteById(final Integer id) {
        return postRepository.findById(id)
                .filter(__ -> postRepository.deletePostById(id) != 0)
                .map(DTOMapper::mapPostToDTO);
    }
}
