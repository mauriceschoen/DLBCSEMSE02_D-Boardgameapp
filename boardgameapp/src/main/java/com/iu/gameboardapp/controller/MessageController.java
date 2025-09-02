package com.iu.gameboardapp.controller;

import com.iu.gameboardapp.service.MessageService;
import dtos.MessageRequestDTO;
import dtos.MessageResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<MessageResponseDTO> sendMessage(@RequestBody MessageRequestDTO dto) {
        return ResponseEntity.ok(messageService.sendMessage(dto));
    }

    @GetMapping
    public ResponseEntity<List<MessageResponseDTO>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/player/{receiverId}")
    public ResponseEntity<List<MessageResponseDTO>> getMessagesForPlayer(@PathVariable Long receiverId) {
        return ResponseEntity.ok(messageService.getMessagesForPlayer(receiverId));
    }

    @GetMapping("/player/{receiverId}/unread")
    public ResponseEntity<List<MessageResponseDTO>> getUnreadMessages(@PathVariable Long receiverId) {
        return ResponseEntity.ok(messageService.getUnreadMessages(receiverId));
    }
}
