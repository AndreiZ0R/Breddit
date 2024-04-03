package com.andreiz0r.breddit.service;

import com.andreiz0r.breddit.controller.message.CreateCommentRequest;
import com.andreiz0r.breddit.controller.message.UpdateCommentRequest;
import com.andreiz0r.breddit.dto.CommentDTO;
import com.andreiz0r.breddit.dto.DTOMapper;
import com.andreiz0r.breddit.model.Comment;
import com.andreiz0r.breddit.repository.CommentRepository;
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
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public List<CommentDTO> findAll() {
        return commentRepository.findAll().stream().map(DTOMapper::mapCommentToDTO).collect(Collectors.toList());
    }

    public Optional<CommentDTO> findById(final Integer id) {
        return commentRepository.findById(id).map(DTOMapper::mapCommentToDTO);
    }

    public Optional<CommentDTO> store(final CreateCommentRequest request) {
        return userRepository.findById(request.getAuthorId())
                .map(author -> DTOMapper.mapCommentToDTO(
                        commentRepository.save(
                                Comment.builder()
                                        .author(author)
                                        .body(request.getBody())
                                        .postId(request.getPostId())
                                        .parentId(request.getParentId())
                                        .postedAt(AppUtils.timestampNow())
                                        .votes(0)
                                        .build()
                        )
                ));
    }

    public Optional<CommentDTO> update(final Integer id, final UpdateCommentRequest request) {
        return commentRepository.findById(id)
                .map(comment -> {
                    DTOMapper.updateValues(comment, request);
                    commentRepository.save(comment);
                    return DTOMapper.mapCommentToDTO(comment);
                });
    }

    public Optional<CommentDTO> deleteById(final Integer id) {
        return commentRepository.findById(id)
                .filter(__ -> commentRepository.deleteCommentById(id) != 0)
                .map(DTOMapper::mapCommentToDTO);
    }
}
