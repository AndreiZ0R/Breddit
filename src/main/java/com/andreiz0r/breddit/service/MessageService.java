package com.andreiz0r.breddit.service;

import com.andreiz0r.breddit.dto.DTOMapper;
import com.andreiz0r.breddit.dto.MessageDTO;
import com.andreiz0r.breddit.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//TODO:
@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public List<MessageDTO> findAll() {
        return messageRepository.findAll().stream().map(DTOMapper::mapMessageToDTO).collect(Collectors.toList());
    }

    public Optional<MessageDTO> findById(final Integer id) {
        return messageRepository.findById(id).map(DTOMapper::mapMessageToDTO);
    }

    public Optional<MessageDTO> deleteById(final Integer id) {
        return messageRepository.findById(id)
                .filter(__ -> messageRepository.deleteMessageById(id) != 0)
                .map(DTOMapper::mapMessageToDTO);
    }
}
