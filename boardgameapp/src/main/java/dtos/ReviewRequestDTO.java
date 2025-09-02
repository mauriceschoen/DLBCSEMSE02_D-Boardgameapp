package dtos;

public record ReviewRequestDTO(
        Long id,
        Long playerId,
        Long gameNightId,
        int hostRating,
        int foodRating,
        int funRating
) {}
