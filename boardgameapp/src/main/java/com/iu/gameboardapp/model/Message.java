package com.iu.gameboardapp.model;

import jakarta.persistence.*;
import com.iu.gameboardapp.model.enums.MessageType;

import java.time.LocalDateTime;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String content;

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    Boolean isRead;


    LocalDateTime sendAt;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player receiver;

    public Message(Long id, String content, MessageType messageType, Boolean isRead, LocalDateTime sendAt, Player receiver) {
        this.id = id;
        this.content = content;
        this.messageType = messageType;
        this.isRead = isRead;
        this.sendAt = sendAt;
        this.receiver = receiver;
    }

    public Message() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public LocalDateTime getSendAt() {
        return sendAt;
    }

    public void setSendAt(LocalDateTime sendAt) {
        this.sendAt = sendAt;
    }

    public Player getReceiver() {
        return receiver;
    }

    public void setReceiver(Player receiver) {
        this.receiver = receiver;
    }
}
