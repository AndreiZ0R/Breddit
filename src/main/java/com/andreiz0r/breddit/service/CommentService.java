package com.andreiz0r.breddit.service;

import com.andreiz0r.breddit.dto.CommentDTO;
import com.andreiz0r.breddit.dto.DTOMapper;
import com.andreiz0r.breddit.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<CommentDTO> findAll() {
        return commentRepository.findAll().stream().map(DTOMapper::mapCommentToDTO).collect(Collectors.toList());
    }

    public Optional<CommentDTO> findById(final Integer id) {
        return commentRepository.findById(id).map(DTOMapper::mapCommentToDTO);
    }

//    public Optional<CommentDTO> storeComment(){}

    public Optional<CommentDTO> deleteById(final Integer id) {
        return commentRepository.deleteByIdAndReturn(id).map(DTOMapper::mapCommentToDTO);
    }
}
