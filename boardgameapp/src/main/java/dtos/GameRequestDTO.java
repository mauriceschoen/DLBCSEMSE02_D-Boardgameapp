package dtos;

import com.iu.gameboardapp.model.enums.DifficultyLevel;

public record GameRequestDTO(
        String name,
        String description,
        Integer minPlayers,
        Integer maxPlayers,
        Integer duration,
        DifficultyLevel difficulty,
        Long proposedById
) {
}


