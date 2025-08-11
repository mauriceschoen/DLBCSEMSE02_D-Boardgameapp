package dtos;

import com.iu.gameboardapp.model.enums.DifficultyLevel;

public record GameResponseDTO(
        Long id,
        String name,
        String description,
        Integer minPlayers,
        Integer maxPlayers,
        Integer duration,
        DifficultyLevel difficulty,
        Long proposedById
) {}
