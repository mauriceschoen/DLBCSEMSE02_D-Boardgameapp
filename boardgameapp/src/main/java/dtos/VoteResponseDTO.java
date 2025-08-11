package dtos;

import com.iu.gameboardapp.model.Player;
import com.iu.gameboardapp.model.enums.VoteType;

import java.time.LocalDateTime;

public record VoteResponseDTO(
        Long id,
        Long playerId,
        Long gameId,
        Long gameNightId,
        VoteType voteValue,
        LocalDateTime votedAt


) {
}

