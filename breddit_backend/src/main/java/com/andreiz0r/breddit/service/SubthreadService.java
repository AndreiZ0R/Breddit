package com.andreiz0r.breddit.service;

import com.andreiz0r.breddit.controller.request.CreateSubthreadRequest;
import com.andreiz0r.breddit.controller.request.UpdateSubthreadRequest;
import com.andreiz0r.breddit.dto.DTOMapper;
import com.andreiz0r.breddit.dto.SubthreadDTO;
import com.andreiz0r.breddit.entity.Subthread;
import com.andreiz0r.breddit.repository.SubthreadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubthreadService {
    private final SubthreadRepository subthreadRepository;

    public List<SubthreadDTO> findAll() {
        return subthreadRepository.findAll().stream().map(DTOMapper::mapSubthreadToDTO).collect(Collectors.toList());
    }

    public Optional<SubthreadDTO> findById(final Integer id) {
        return subthreadRepository.findById(id).map(DTOMapper::mapSubthreadToDTO);
    }

    public Optional<SubthreadDTO> store(final CreateSubthreadRequest request) {
        return Optional.of(
                DTOMapper.mapSubthreadToDTO(
                        subthreadRepository.save(
                                Subthread.builder()
                                        .name(request.getName())
                                        .description(request.getDescription())
                                        .membersCount(0)
                                        .posts(List.of())
                                        .build()
                        )
                ));
    }

    public Optional<SubthreadDTO> update(final Integer id, final UpdateSubthreadRequest request) {
        return subthreadRepository.findById(id)
                .map(subthread -> {
                    DTOMapper.updateValues(subthread, request);
                    subthreadRepository.save(subthread);
                    return DTOMapper.mapSubthreadToDTO(subthread);
                });
    }

    public Optional<SubthreadDTO> deleteById(final Integer id) {
        return subthreadRepository.findById(id)
                .filter(__ -> subthreadRepository.deleteSubthreadById(id) != 0)
                .map(DTOMapper::mapSubthreadToDTO);
    }
}
