package dtos;
import com.iu.gameboardapp.model.enums.MessageType;

public record MessageRequestDTO(
        Long id,
        String content,
        MessageType messageType,
        Boolean isRead,
        Long receiverId
) {}
