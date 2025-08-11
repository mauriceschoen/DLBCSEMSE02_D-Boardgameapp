package com.iu.gameboardapp.controller;

import com.iu.gameboardapp.model.Game;
import com.iu.gameboardapp.repository.VoteRepository;
import com.iu.gameboardapp.service.VoteService;
import dtos.GameResponseDTO;
import dtos.VoteResponseDTO;
import mapper.GameMapper;
import mapper.VoteMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.iu.gameboardapp.model.enums.VoteType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class VoteController {

    private final VoteService voteService;
    private final VoteRepository voteRepository;

    public VoteController(VoteService voteService, VoteRepository voteRepository) {
        this.voteService = voteService;
        this.voteRepository = voteRepository;
    }

    // cast votes
    @PostMapping("/vote/players/{playerId}/games/{gameId}/votes")
    public ResponseEntity<VoteResponseDTO> castVote(
            @PathVariable Long gameId,
            @PathVariable Long playerId,
            @RequestParam VoteType voteValue) {
        VoteResponseDTO cast = voteService.castVote(gameId, playerId, voteValue);
        return ResponseEntity.ok(cast);
    }

    // get votes by suggestion
    @GetMapping("/vote/bySuggestion/{proposedById}")
    public ResponseEntity<List<VoteResponseDTO>> getVotesBySuggestion(@PathVariable Long proposedById) {
        List<VoteResponseDTO> votes = voteService.getVotesBySuggestion(proposedById);
        if (votes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(votes);
    }

    //get votes by player id
    @GetMapping("/vote/byPlayerId/player/{playerId}/pr/{proposedById}")
    public ResponseEntity<List<VoteResponseDTO>> getVoteByPlayer(
            @PathVariable Long playerId,
            @PathVariable Long proposedById) {
        List<VoteResponseDTO> votes = voteService.getVoteByPlayer(playerId, proposedById);
        if (votes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(votes);
    }

    //update vote
    @PutMapping("/vote/update/{voteId}")
    public ResponseEntity<VoteResponseDTO> updateVote(@PathVariable Long voteId, @RequestParam VoteType updatedVote) {
        try {
            VoteResponseDTO savedVote = voteService.updateVote(voteId, updatedVote);
            return ResponseEntity.ok(savedVote);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // count the votes per game
    @PostMapping("/vote/gn/{gameNightId}")
    public ResponseEntity<Map<Long, Long>> calculateVotingResults(@PathVariable Long gameNightId) {
        Map<Long, Long> votes = voteService.calculateVotingResults(gameNightId);

        if (votes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(votes);


    }


    @GetMapping("/vote/win/{gameNightId}")
    public ResponseEntity<List<GameResponseDTO>> getWinningGame(@PathVariable Long gameNightId) {
        List<Game> countGames = voteService.getWinningGame(gameNightId);

        if (countGames.isEmpty()) {
            return ResponseEntity.noContent().build();
        }


        List<GameResponseDTO> dtoList = GameMapper.toResponseDtoList(countGames);
        return ResponseEntity.ok(dtoList);
    }

}
