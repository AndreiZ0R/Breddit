package com.andreiz0r.breddit.service;

import com.andreiz0r.breddit.controller.request.UpdateSubthreadRequest;
import com.andreiz0r.breddit.dto.DTOMapper;
import com.andreiz0r.breddit.dto.SubthreadDTO;
import com.andreiz0r.breddit.entity.Subthread;
import com.andreiz0r.breddit.repository.SubthreadRepository;
import com.andreiz0r.breddit.utils.AbstractUnitTest;
import com.andreiz0r.breddit.utils.Randoms;
import com.andreiz0r.breddit.utils.SubthreadUtils;
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

class SubthreadServiceTest extends AbstractUnitTest<Subthread> {
    private SubthreadService sut;
    private SubthreadRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(SubthreadRepository.class);
        sut = new SubthreadService(repository);
    }

    @Test
    void findAll_hasSubthreads_returnsList() {
        // Given
        List<Subthread> subthreads = List.of(SubthreadUtils.createRandomSubthread(), SubthreadUtils.createRandomSubthread(), SubthreadUtils.createRandomSubthread());
        when(repository.findAll()).thenReturn(subthreads);

        // When
        List<SubthreadDTO> response = sut.findAll();

        // Then
        List<SubthreadDTO> expectedResponse = subthreads.stream().map(DTOMapper::mapSubthreadToDTO).toList();
        verify(repository).findAll();
        assertThat(response, equalTo(expectedResponse));
    }

    @Test
    void findAll_doesNotHaveSubthreads_returnsEmptyList() {
        // Given
        when(repository.findAll()).thenReturn(List.of());

        // When
        List<SubthreadDTO> response = sut.findAll();

        // Then
        verify(repository).findAll();
        assertThat(response.isEmpty(), equalTo(true));
    }

    @Test
    void findById_validId_returnsSubthread() {
        // Given
        Integer id = Randoms.randomPositiveInteger();
        Subthread subthread = SubthreadUtils.createRandomSubthread(id);
        when(repository.findById(id)).thenReturn(Optional.of(subthread));

        // When
        Optional<SubthreadDTO> response = sut.findById(id);

        // Then
        verify(repository).findById(id);
        response.ifPresentOrElse(
                SubthreadDTO -> assertThatSubthreadDTOIsValid(SubthreadDTO, subthread),
                this::assertThatFails);
    }

    @Test
    void findById_invalidId_returnsEmpty() {
        // Given
        Integer id = Randoms.randomPositiveInteger();

        // When
        Optional<SubthreadDTO> response = sut.findById(id);

        // Then
        verify(repository).findById(id);
        response.ifPresent(this::assertThatFails);
    }

    @Test
    void update_propertyChanged_returnsUpdatedSubthread() {
        // Given
        Integer id = Randoms.randomPositiveInteger();
        Subthread subthread = SubthreadUtils.createRandomSubthread(id);
        when(repository.findById(id)).thenReturn(Optional.of(subthread));

        UpdateSubthreadRequest request = new UpdateSubthreadRequest();
        String newSubthreadName = Randoms.alphabetic();
        request.setName(newSubthreadName);

        // When
        Optional<SubthreadDTO> response = sut.update(id, request);

        // Then
        verify(repository).save(subthread);
        response.ifPresentOrElse(
                SubthreadDTO -> assertThatSubthreadDTOIsValid(SubthreadDTO, subthread),
                this::assertThatFails);
    }

    @Test
    void update_invalidId_returnsEmpty() {
        // Given
        Integer id = Randoms.randomPositiveInteger();

        UpdateSubthreadRequest request = new UpdateSubthreadRequest();
        String newSubthreadName = Randoms.alphabetic();
        request.setName(newSubthreadName);

        // When
        Optional<SubthreadDTO> response = sut.update(id, request);

        // Then
        verify(repository, never()).save(any());
        response.ifPresent(this::assertThatFails);
    }

    @Test
    void deleteById_validId_returnsDeletedSubthread() {
        // Given
        Integer id = Randoms.randomPositiveInteger();
        Subthread subthread = SubthreadUtils.createRandomSubthread(id);
        when(repository.findById(id)).thenReturn(Optional.of(subthread));
        when(repository.deleteSubthreadById(id)).thenReturn(1);

        // When
        Optional<SubthreadDTO> response = sut.deleteById(id);

        // Then
        verify(repository).deleteSubthreadById(id);
        response.ifPresentOrElse(
                SubthreadDTO -> assertThatSubthreadDTOIsValid(SubthreadDTO, subthread),
                this::assertThatFails);
    }

    @Test
    void deleteById_invalidId_returnsEmpty() {
        // Given
        Integer id = Randoms.randomPositiveInteger();

        // When
        Optional<SubthreadDTO> response = sut.deleteById(id);

        // Then
        verify(repository, never()).deleteSubthreadById(id);
        response.ifPresent(this::assertThatFails);
    }

    private void assertThatSubthreadDTOIsValid(final SubthreadDTO subthreadDTO, final Subthread subthread) {
        assertThat(subthreadDTO, equalTo(DTOMapper.mapSubthreadToDTO(subthread)));
    }
}