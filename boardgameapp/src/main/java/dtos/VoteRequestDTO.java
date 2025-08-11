package dtos;

import com.iu.gameboardapp.model.Game;
import com.iu.gameboardapp.model.enums;

import java.time.LocalDateTime;

public record VoteRequestDTO(
        Long id,
        Long playerId,
        Long gameId,
        Long gameNightId,
        enums.VoteType voteValue,
        LocalDateTime votedAt


) {}



