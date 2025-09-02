package com.iu.gameboardapp.repository;

import com.iu.gameboardapp.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

//   List<Message> findByGameNightId(Long gameNighId);
//   List<Message> findBySenderIdAndIsReadFalse(Long senderId);
//   List<Message> findByGameNightIdOrderBySentByDesc(Long gameNightId);

   // Alle Nachrichten für einen bestimmten Empfänger
   List<Message> findByReceiverId(Long receiverId);

   // Alle ungelesenen Nachrichten für einen bestimmten Empfänger
   List<Message> findByReceiverIdAndIsReadFalse(Long receiverId);

   // Nachrichten sortiert nach Sendezeit (optional nützlich)
   List<Message> findAllByOrderBySendAtDesc();

}
