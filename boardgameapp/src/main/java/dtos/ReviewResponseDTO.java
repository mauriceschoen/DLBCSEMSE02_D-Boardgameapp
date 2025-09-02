package dtos;

public record ReviewResponseDTO(
        Long id,
        String playerName,
        int hostRating,
        int foodRating,
        int funRating
) {}
