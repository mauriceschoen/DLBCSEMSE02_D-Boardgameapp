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

}
