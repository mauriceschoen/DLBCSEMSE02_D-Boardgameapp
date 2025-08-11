package mapper;

import com.iu.gameboardapp.model.Player;
import dtos.PlayerResponseDTO;

//dto mapping for player service
public class PlayerMapper {
    public static PlayerResponseDTO toDto(Player player) {
        return new PlayerResponseDTO(
                player.getId(),
                player.getName(),
                player.getEmail(),
                player.getPhoneNumber(),
                player.getFoodPreference()
        );
    }
}


