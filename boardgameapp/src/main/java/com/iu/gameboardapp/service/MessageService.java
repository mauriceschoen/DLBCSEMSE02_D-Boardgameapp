package com.iu.gameboardapp.service;

import com.iu.gameboardapp.model.Message;
import com.iu.gameboardapp.model.Player;
import com.iu.gameboardapp.repository.MessageRepository;
import com.iu.gameboardapp.repository.PlayerRepository;
import dtos.MessageRequestDTO;
import dtos.MessageResponseDTO;
import mapper.MessageMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final PlayerRepository playerRepository;

    public MessageService(MessageRepository messageRepository, PlayerRepository playerRepository) {
        this.messageRepository = messageRepository;
        this.playerRepository = playerRepository;
    }

    public MessageResponseDTO sendMessage(MessageRequestDTO dto) {
        Player receiver = playerRepository.findById(dto.receiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Message msg = MessageMapper.toEntity(dto, receiver);
        msg.setSendAt(LocalDateTime.now());
        Message saved = messageRepository.save(msg);

        return MessageMapper.toResponseDTO(saved);
    }

    public List<MessageResponseDTO> getAllMessages() {
        return messageRepository.findAll()
                .stream()
                .map(MessageMapper::toResponseDTO)
                .toList();
    }

    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }

    public List<MessageResponseDTO> getMessagesForPlayer(Long receiverId) {
        return messageRepository.findByReceiverId(receiverId)
                .stream()
                .map(MessageMapper::toResponseDTO)
                .toList();
    }

    public List<MessageResponseDTO> getUnreadMessages(Long receiverId) {
        return messageRepository.findByReceiverIdAndIsReadFalse(receiverId)
                .stream()
                .map(MessageMapper::toResponseDTO)
                .toList();
    }
}
