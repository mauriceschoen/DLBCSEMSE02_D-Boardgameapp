package mapper;

import com.iu.gameboardapp.model.Game;
import com.iu.gameboardapp.model.Player;
import dtos.GameRequestDTO;
import dtos.GameResponseDTO;

import java.util.List;

public class GameMapper {

    public static GameRequestDTO toGameDto(Game game) {
        Long proposedById = null;
        if (game.getProposedBy() != null) {
            proposedById = game.getProposedBy().getId();
        }

        return new GameRequestDTO(
                game.getName(),
                game.getDescription(),
                game.getMinPlayers(),
                game.getMaxPlayers(),
                game.getDuration(),
                game.getDifficulty(),
                proposedById
        );
    }

    public static Game toGameEntity(GameRequestDTO dto, Player proposedBy) {
        Game game = new Game();
        game.setName(dto.name());
        game.setDescription(dto.description());
        game.setMinPlayers(dto.minPlayers());
        game.setMaxPlayers(dto.maxPlayers());
        game.setDuration(dto.duration());
        game.setDifficulty(dto.difficulty());
        game.setProposedBy(proposedBy);
        return game;
    }

    public static GameResponseDTO toResponseDto(Game game) {
        Long proposedById = null;
        if (game.getProposedBy() != null) {
            proposedById = game.getProposedBy().getId();
        }

        return new GameResponseDTO(
                game.getId(),
                game.getName(),
                game.getDescription(),
                game.getMinPlayers(),
                game.getMaxPlayers(),
                game.getDuration(),
                game.getDifficulty(),
                proposedById
        );
    }

    public static List<GameResponseDTO> toResponseDtoList(List<Game> games) {
        return games.stream()
                .map(GameMapper::toResponseDto)
                .toList();
    }
}

