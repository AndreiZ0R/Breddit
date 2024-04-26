package com.andreiz0r.breddit.service;

import com.andreiz0r.breddit.controller.request.UpdateCommentRequest;
import com.andreiz0r.breddit.dto.CommentDTO;
import com.andreiz0r.breddit.dto.DTOMapper;
import com.andreiz0r.breddit.entity.Comment;
import com.andreiz0r.breddit.repository.CommentRepository;
import com.andreiz0r.breddit.repository.UserRepository;
import com.andreiz0r.breddit.utils.AbstractUnitTest;
import com.andreiz0r.breddit.utils.CommentUtils;
import com.andreiz0r.breddit.utils.Randoms;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CommentServiceTest extends AbstractUnitTest<Comment> {
    private CommentService sut;
    private CommentRepository repository;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        repository = mock(CommentRepository.class);
        userRepository = mock(UserRepository.class);
        sut = new CommentService(repository, userRepository);
    }

    @Test
    void findAll_hasComments_returnsList() {
        // Given
        List<Comment> comments = List.of(CommentUtils.createRandomComment(), CommentUtils.createRandomComment(), CommentUtils.createRandomComment());
        when(repository.findAll()).thenReturn(comments);

        // When
        List<CommentDTO> response = sut.findAll();

        // Then
        List<CommentDTO> expectedResponse = comments.stream().map(DTOMapper::mapCommentToDTO).toList();
        verify(repository).findAll();
        assertThat(response, equalTo(expectedResponse));
    }

    @Test
    void findAll_doesNotHaveComments_returnsEmptyList() {
        // Given
        when(repository.findAll()).thenReturn(List.of());

        // When
        List<CommentDTO> response = sut.findAll();

        // Then
        verify(repository).findAll();
        assertThat(response.isEmpty(), equalTo(true));
    }

    @Test
    void findById_validId_returnsComment() {
        // Given
        Integer id = Randoms.randomPositiveInteger();
        Comment comment = CommentUtils.createRandomComment(id);
        when(repository.findById(id)).thenReturn(Optional.of(comment));

        // When
        Optional<CommentDTO> response = sut.findById(id);

        // Then
        verify(repository).findById(id);
        response.ifPresentOrElse(
                CommentDTO -> assertThatCommentDTOIsValid(CommentDTO, comment),
                this::assertThatFails);
    }

    @Test
    void findById_invalidId_returnsEmpty() {
        // Given
        Integer id = Randoms.randomPositiveInteger();

        // When
        Optional<CommentDTO> response = sut.findById(id);

        // Then
        verify(repository).findById(id);
        response.ifPresent(this::assertThatFails);
    }

    @Test
    void update_propertyChanged_returnsUpdatedComment() {
        // Given
        Integer id = Randoms.randomPositiveInteger();
        Comment comment = CommentUtils.createRandomComment(id);
        when(repository.findById(id)).thenReturn(Optional.of(comment));
        when(userRepository.findById(comment.getAuthor().getId()))
                .thenReturn(Optional.of(comment.getAuthor()));

        UpdateCommentRequest request = new UpdateCommentRequest();
        String newCommentBody = Randoms.alphabetic();
        request.setBody(newCommentBody);

        // When
        Optional<CommentDTO> response = sut.update(id, request);

        // Then
        verify(repository).save(comment);
        response.ifPresentOrElse(
                CommentDTO -> assertThatCommentDTOIsValid(CommentDTO, comment),
                this::assertThatFails);
    }

    @Test
    void update_invalidId_returnsEmpty() {
        // Given
        Integer id = Randoms.randomPositiveInteger();

        UpdateCommentRequest request = new UpdateCommentRequest();
        String newCommentBody = Randoms.alphabetic();
        request.setBody(newCommentBody);

        // When
        Optional<CommentDTO> response = sut.update(id, request);

        // Then
        verify(repository, never()).save(any());
        response.ifPresent(this::assertThatFails);
    }

    @Test
    void deleteById_validId_returnsDeletedComment() {
        // Given
        Integer id = Randoms.randomPositiveInteger();
        Comment comment = CommentUtils.createRandomComment(id);
        when(repository.findById(id)).thenReturn(Optional.of(comment));
        when(repository.deleteCommentById(id)).thenReturn(1);

        // When
        Optional<CommentDTO> response = sut.deleteById(id);

        // Then
        verify(repository).deleteCommentById(id);
        response.ifPresentOrElse(
                CommentDTO -> assertThatCommentDTOIsValid(CommentDTO, comment),
                this::assertThatFails);
    }

    @Test
    void deleteById_invalidId_returnsEmpty() {
        // Given
        Integer id = Randoms.randomPositiveInteger();

        // When
        Optional<CommentDTO> response = sut.deleteById(id);

        // Then
        verify(repository, never()).deleteCommentById(id);
        response.ifPresent(this::assertThatFails);
    }

    private void assertThatCommentDTOIsValid(final CommentDTO commentDTO, final Comment comment) {
        assertThat(commentDTO, equalTo(DTOMapper.mapCommentToDTO(comment)));
    }
}