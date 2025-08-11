package mapper;

import com.iu.gameboardapp.model.GameNight;
import com.iu.gameboardapp.model.Player;
import dtos.GameNightResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class GameNightMapper {
    public static GameNightResponseDTO toDto(GameNight gamenight) {
        List<Long> participantIds = gamenight.getParticipants()
                .stream()
                .map(Player::getId) // gets the id from every player
                .collect(Collectors.toList());

        return new GameNightResponseDTO(
                gamenight.getId(),
                gamenight.getDate(),
                gamenight.getLocation(),
                gamenight.getStatus(),
                gamenight.getMaxParticipants(),
                gamenight.getHost().getId(),
                participantIds
        );
    }
}
