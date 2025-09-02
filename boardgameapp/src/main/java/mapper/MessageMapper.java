package mapper;

import com.iu.gameboardapp.model.Message;
import com.iu.gameboardapp.model.Player;
import dtos.MessageRequestDTO;
import dtos.MessageResponseDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MessageMapper {

    public static Message toEntity(MessageRequestDTO dto, Player receiver) {
        Message msg = new Message();
        msg.setId(dto.id());
        msg.setContent(dto.content());
        msg.setMessageType(dto.messageType());
        msg.setRead(false); // default: ungelesen
        msg.setSendAt(LocalDateTime.now()); // hier gesetzt
        msg.setReceiver(receiver);
        return msg;
    }

    public static MessageResponseDTO toResponseDTO(Message msg) {
        return new MessageResponseDTO(
                msg.getId(),
                msg.getContent(),
                msg.getMessageType(),
                msg.getRead(),
                msg.getSendAt(),
                msg.getReceiver() != null ? msg.getReceiver().getId() : null
        );
    }

}
