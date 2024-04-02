package com.andreiz0r.breddit.service;

import com.andreiz0r.breddit.controller.message.UpdateUserRequest;
import com.andreiz0r.breddit.dto.DTOMapper;
import com.andreiz0r.breddit.dto.UserDTO;
import com.andreiz0r.breddit.model.User;
import com.andreiz0r.breddit.repository.UserRepository;
import com.andreiz0r.breddit.utils.AbstractUnitTest;
import com.andreiz0r.breddit.utils.Randoms;
import com.andreiz0r.breddit.utils.UserUtils;
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


class UserServiceTest extends AbstractUnitTest<User> {
    private UserService sut;
    private UserRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(UserRepository.class);
        sut = new UserService(repository);
    }

    @Test
    void findAll_hasUsers_returnsList() {
        // Given
        List<User> users = List.of(UserUtils.createRandomUser(), UserUtils.createRandomUser(), UserUtils.createRandomUser());
        when(repository.findAll()).thenReturn(users);

        // When
        List<UserDTO> response = sut.findAll();

        // Then
        List<UserDTO> expectedResponse = users.stream().map(DTOMapper::mapUserToDTO).toList();
        verify(repository).findAll();
        assertThat(response, equalTo(expectedResponse));
    }

    @Test
    void findAll_doesNotHaveUsers_returnsEmptyList() {
        // Given
        when(repository.findAll()).thenReturn(List.of());

        // When
        List<UserDTO> response = sut.findAll();

        // Then
        verify(repository).findAll();
        assertThat(response.isEmpty(), equalTo(true));
    }

    @Test
    void findById_validId_returnsUser() {
        // Given
        Integer id = Randoms.randomPositiveInteger();
        User user = UserUtils.createRandomUser(id);
        when(repository.findById(id)).thenReturn(Optional.of(user));

        // When
        Optional<UserDTO> response = sut.findById(id);

        // Then
        verify(repository).findById(id);
        response.ifPresentOrElse(
                userDTO -> assertThatUserDTOIsValid(userDTO, user),
                this::assertThatFails);
    }

    @Test
    void findById_invalidId_returnsEmpty() {
        // Given
        Integer id = Randoms.randomPositiveInteger();

        // When
        Optional<UserDTO> response = sut.findById(id);

        // Then
        verify(repository).findById(id);
        response.ifPresent(this::assertThatFails);
    }

    @Test
    void findByUsername_usernameIsValid_returnsUser() {
        // Given
        String username = Randoms.alphabetic();
        User user = UserUtils.createRandomUser(username);
        when(repository.findByUsername(username)).thenReturn(Optional.of(user));

        // When
        Optional<UserDTO> response = sut.findByUsername(username);

        // Then
        verify(repository).findByUsername(username);
        response.ifPresentOrElse(
                userDTO -> assertThatUserDTOIsValid(userDTO, user),
                this::assertThatFails);
    }

    @Test
    void findByUsername_usernameIsNotValid_returnsEmpty() {
        // Given
        String username = Randoms.alphabetic();

        // When
        Optional<UserDTO> response = sut.findByUsername(username);

        // Then
        verify(repository).findByUsername(username);
        response.ifPresent(this::assertThatFails);
    }

    @Test
    void update_propertyChanged_returnsUpdatedUser() {
        // Given
        Integer id = Randoms.randomPositiveInteger();
        User user = UserUtils.createRandomUser(id);
        when(repository.findById(id)).thenReturn(Optional.of(user));

        UpdateUserRequest request = new UpdateUserRequest();
        String newUsername = Randoms.alphabetic();
        request.setUsername(newUsername);

        // When
        Optional<UserDTO> response = sut.update(id, request);

        // Then
        verify(repository).save(user);
        response.ifPresentOrElse(
                userDTO -> assertThatUserDTOIsValid(userDTO, user),
                this::assertThatFails);
    }

    @Test
    void update_invalidId_returnsEmpty() {
        // Given
        Integer id = Randoms.randomPositiveInteger();

        UpdateUserRequest request = new UpdateUserRequest();
        String newUsername = Randoms.alphabetic();
        request.setUsername(newUsername);

        // When
        Optional<UserDTO> response = sut.update(id, request);

        // Then
        verify(repository, never()).save(any());
        response.ifPresent(this::assertThatFails);
    }

    @Test
    void deleteById_validId_returnsDeletedUser() {
        // Given
        Integer id = Randoms.randomPositiveInteger();
        User user = UserUtils.createRandomUser(id);
        when(repository.findById(id)).thenReturn(Optional.of(user));
        when(repository.deleteUserById(id)).thenReturn(1);

        // When
        Optional<UserDTO> response = sut.deleteById(id);

        // Then
        verify(repository).deleteUserById(id);
        response.ifPresentOrElse(
                userDTO -> assertThatUserDTOIsValid(userDTO, user),
                this::assertThatFails);
    }

    @Test
    void deleteById_invalidId_returnsEmpty() {
        // Given
        Integer id = Randoms.randomPositiveInteger();

        // When
        Optional<UserDTO> response = sut.deleteById(id);

        // Then
        verify(repository, never()).deleteUserById(id);
        response.ifPresent(this::assertThatFails);
    }

    private void assertThatUserDTOIsValid(final UserDTO userDTO, final User user) {
        assertThat(userDTO, equalTo(DTOMapper.mapUserToDTO(user)));
    }
}