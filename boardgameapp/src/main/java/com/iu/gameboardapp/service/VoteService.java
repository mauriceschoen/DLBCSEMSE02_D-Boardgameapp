package com.iu.gameboardapp.service;

import com.iu.gameboardapp.model.Game;
import com.iu.gameboardapp.model.GameNight;
import com.iu.gameboardapp.model.Player;
import com.iu.gameboardapp.model.Vote;
import com.iu.gameboardapp.repository.GameNightRepository;
import com.iu.gameboardapp.repository.GameRepository;
import com.iu.gameboardapp.repository.PlayerRepository;
import com.iu.gameboardapp.repository.VoteRepository;
import dtos.VoteResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import mapper.VoteMapper;
import org.springframework.stereotype.Service;
import com.iu.gameboardapp.model.enums.VoteType;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final PlayerRepository playerRepository;
    private final GameNightRepository gameNightRepository;
    private final GameRepository gameRepository;

    public VoteService(VoteRepository voteRepository,
                       PlayerRepository playerRepository,
                       GameNightRepository gameNightRepository,
                       GameRepository gameRepository) {
        this.voteRepository = voteRepository;
        this.playerRepository = playerRepository;
        this.gameNightRepository = gameNightRepository;
        this.gameRepository = gameRepository;
    }

    ;


// cast votes
@Transactional
public VoteResponseDTO castVote(Long gameId, Long playerId, VoteType voteValue) {

    //get Player
    Player player = playerRepository.findById(playerId)
            .orElseThrow(() -> new EntityNotFoundException("Player not found"));

    //get game
    Game game = gameRepository.findById(gameId)
            .orElseThrow(() -> new EntityNotFoundException("Game not found"));

    //get gameNight
    GameNight gameNight = gameNightRepository.findFirstByDateAfterOrderByDateAsc(LocalDateTime.now())
            .orElseThrow(() -> new EntityNotFoundException("GameNight not found"));

    // suggestion id



    //create vote
    Vote vote = new Vote(player, game, gameNight, voteValue, LocalDateTime.now());

    //save
    voteRepository.save(vote);

    return VoteMapper.toResponseDto(vote);
}


// get votes suggestion
@Transactional
 public List<VoteResponseDTO> getVotesBySuggestion(Long proposedById) {
    return voteRepository.findByGameProposedById(proposedById)
            .stream()
            .map(VoteMapper::toResponseDto)
            .collect(Collectors.toList());

}

// get vote by player id
    public List<VoteResponseDTO> getVoteByPlayer(Long playerId, Long proposedId) {
        return voteRepository.findByPlayerIdAndGameProposedById(playerId, proposedId)
                .stream()
                .map(VoteMapper:: toResponseDto)
                .collect(Collectors.toList());

    }

    // update vote
    public VoteResponseDTO updateVote(Long voteId, VoteType updatedVote) {
      Vote vote = voteRepository.findById(voteId)
              .orElseThrow(() -> new EntityNotFoundException(("Vote with the id " + voteId + " not found")));

      vote.setVoteValue(updatedVote); // update VoteType

        Vote savedVote = voteRepository.save(vote); // save

        return VoteMapper.toResponseDto(savedVote); // return DTO


    }

    //calculate Voting results
    public Map<Long, Long> calculateVotingResults(Long gameNightId) {
    GameNight gameNight = gameNightRepository.findById(gameNightId)
            .orElseThrow(() -> new EntityNotFoundException(("Game Night with the id " + gameNightId + " not found")));

    // get all votes from GameNight
        List<Vote> votes = voteRepository.findByGameNightId(gameNightId);

        // count the votes per game
        return votes.stream()
                .collect(Collectors.groupingBy(
                        vote -> vote.getGame().getId(),
                        Collectors.counting()
                ));

    }
        //alternative to the stream
//        for (Vote vote : votes) {
//            Long gameId = vote.getGame().getId();
//            result.put(gameId, result.getOrDefault(gameId, 0) + 1);
//        }

//get winning game
public List<Game> getWinningGame(Long gameNightId) {
    GameNight gameNight = gameNightRepository.findById(gameNightId)
            .orElseThrow(() -> new EntityNotFoundException("Game Night with id " + gameNightId + " not found"));

    List<Vote> votes = voteRepository.findByGameNightId(gameNightId);

   //Group and count votes by game
    Map<Game, Long> votePerGame = votes.stream()
            .collect(Collectors.groupingBy(Vote::getGame, Collectors.counting()));

    //Find maximum number
    long maxVotes = votePerGame.values().stream()
            .mapToLong(Long::longValue)
            .max()
            .orElse(0);

    //Collect all games with the maximum number
    return votePerGame.entrySet().stream()
            .filter(entry -> entry.getValue() == maxVotes)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
}

}
