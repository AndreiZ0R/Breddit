package com.andreiz0r.breddit.service;

import com.andreiz0r.breddit.dto.DTOMapper;
import com.andreiz0r.breddit.dto.SubthreadDTO;
import com.andreiz0r.breddit.model.Subthread;
import com.andreiz0r.breddit.repository.SubthreadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubthreadService {
    private final SubthreadRepository subthreadRepository;

    public List<SubthreadDTO> findAll() {
        return subthreadRepository.findAll().stream().map(DTOMapper::mapSubthreadToDTO).collect(Collectors.toList());
    }

    public Optional<SubthreadDTO> findById(final Integer id) {
        return subthreadRepository.findById(id).map(DTOMapper::mapSubthreadToDTO);
    }

    public Optional<SubthreadDTO> deleteById(final Integer id) {
        Optional<Subthread> user = subthreadRepository.findById(id);
        Integer value = subthreadRepository.deleteSubthreadById(id);

        return user.filter(__ -> value != 0).map(DTOMapper::mapSubthreadToDTO);
    }
}
