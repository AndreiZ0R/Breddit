package com.andreiz0r.breddit.service;

import com.andreiz0r.breddit.controller.request.UpdatePostRequest;
import com.andreiz0r.breddit.dto.DTOMapper;
import com.andreiz0r.breddit.dto.PostDTO;
import com.andreiz0r.breddit.entity.Post;
import com.andreiz0r.breddit.repository.PostRepository;
import com.andreiz0r.breddit.repository.UserRepository;
import com.andreiz0r.breddit.utils.AbstractUnitTest;
import com.andreiz0r.breddit.utils.PostUtils;
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

class PostServiceTest extends AbstractUnitTest<Post> {
    private PostService sut;
    private PostRepository repository;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        repository = mock(PostRepository.class);
        userRepository = mock(UserRepository.class);
        sut = new PostService(repository, userRepository);
    }

    @Test
    void findAll_hasPosts_returnsList() {
        // Given
        List<Post> posts = List.of(PostUtils.createRandomPost(), PostUtils.createRandomPost(), PostUtils.createRandomPost());
        when(repository.findAll()).thenReturn(posts);

        // When
        List<PostDTO> response = sut.findAll();

        // Then
        List<PostDTO> expectedResponse = posts.stream().map(DTOMapper::mapPostToDTO).toList();
        verify(repository).findAll();
        assertThat(response, equalTo(expectedResponse));
    }

    @Test
    void findAll_doesNotHavePosts_returnsEmptyList() {
        // Given
        when(repository.findAll()).thenReturn(List.of());

        // When
        List<PostDTO> response = sut.findAll();

        // Then
        verify(repository).findAll();
        assertThat(response.isEmpty(), equalTo(true));
    }

    @Test
    void findById_validId_returnsPost() {
        // Given
        Integer id = Randoms.randomPositiveInteger();
        Post post = PostUtils.createRandomPost(id);
        when(repository.findById(id)).thenReturn(Optional.of(post));

        // When
        Optional<PostDTO> response = sut.findById(id);

        // Then
        verify(repository).findById(id);
        response.ifPresentOrElse(
                PostDTO -> assertThatPostDTOIsValid(PostDTO, post),
                this::assertThatFails);
    }

    @Test
    void findById_invalidId_returnsEmpty() {
        // Given
        Integer id = Randoms.randomPositiveInteger();

        // When
        Optional<PostDTO> response = sut.findById(id);

        // Then
        verify(repository).findById(id);
        response.ifPresent(this::assertThatFails);
    }

    @Test
    void update_propertyChanged_returnsUpdatedPost() {
        // Given
        Integer id = Randoms.randomPositiveInteger();
        Post post = PostUtils.createRandomPost(id);
        when(repository.findById(id)).thenReturn(Optional.of(post));
        when(userRepository.findById(post.getAuthor().getId()))
                .thenReturn(Optional.of(post.getAuthor()));

        UpdatePostRequest request = new UpdatePostRequest();
        String newPostTitle = Randoms.alphabetic();
        request.setTitle(newPostTitle);

        // When
        Optional<PostDTO> response = sut.update(id, request);

        // Then
        verify(repository).save(post);
        response.ifPresentOrElse(
                PostDTO -> assertThatPostDTOIsValid(PostDTO, post),
                this::assertThatFails);
    }

    @Test
    void update_invalidId_returnsEmpty() {
        // Given
        Integer id = Randoms.randomPositiveInteger();

        UpdatePostRequest request = new UpdatePostRequest();
        String newPostTitle = Randoms.alphabetic();
        request.setTitle(newPostTitle);

        // When
        Optional<PostDTO> response = sut.update(id, request);

        // Then
        verify(repository, never()).save(any());
        response.ifPresent(this::assertThatFails);
    }

    @Test
    void deleteById_validId_returnsDeletedPost() {
        // Given
        Integer id = Randoms.randomPositiveInteger();
        Post post = PostUtils.createRandomPost(id);
        when(repository.findById(id)).thenReturn(Optional.of(post));
        when(repository.deletePostById(id)).thenReturn(1);

        // When
        Optional<PostDTO> response = sut.deleteById(id);

        // Then
        verify(repository).deletePostById(id);
        response.ifPresentOrElse(
                PostDTO -> assertThatPostDTOIsValid(PostDTO, post),
                this::assertThatFails);
    }

    @Test
    void deleteById_invalidId_returnsEmpty() {
        // Given
        Integer id = Randoms.randomPositiveInteger();

        // When
        Optional<PostDTO> response = sut.deleteById(id);

        // Then
        verify(repository, never()).deletePostById(id);
        response.ifPresent(this::assertThatFails);
    }

    private void assertThatPostDTOIsValid(final PostDTO postDTO, final Post post) {
        assertThat(postDTO, equalTo(DTOMapper.mapPostToDTO(post)));
    }
}