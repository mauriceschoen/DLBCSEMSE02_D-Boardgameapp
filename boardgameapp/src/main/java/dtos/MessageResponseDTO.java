package dtos;

import java.time.LocalDateTime;
import com.iu.gameboardapp.model.enums.MessageType;

public record MessageResponseDTO(
        Long id,
        String content,
        MessageType messageType,
        Boolean isRead,
        LocalDateTime sendAt,
        Long receiverId
) {}
