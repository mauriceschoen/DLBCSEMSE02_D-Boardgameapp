package com.iu.gameboardapp.service;

import com.iu.gameboardapp.model.Game;
import com.iu.gameboardapp.model.Player;
import com.iu.gameboardapp.repository.GameRepository;
import com.iu.gameboardapp.repository.PlayerRepository;
import dtos.GameRequestDTO;
import dtos.GameResponseDTO;
import mapper.GameMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class GameService {


    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;


    public GameService(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    // retrieve game
    public ResponseEntity<Game> getGameById(@RequestParam(value = "id") long id) {
        return gameRepository.findById(id)
                .map(ResponseEntity::ok) // returns game if id is present
                .orElseGet(() -> ResponseEntity.notFound().build());

    }
        // Game newGame = new Game();
        //  Optional<Game> gameInDb = gamesRepository.findById((long) id);

        //   ? new ResponseEntity<Game>(gameInDb.get(), HttpStatus.OK) // conventional code
        // : new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // create a game
    public ResponseEntity<GameResponseDTO> createGame(GameRequestDTO dto) {
        Player proposedBy = playerRepository.findById(dto.proposedById())
                .orElseThrow(() -> new RuntimeException("Player not found with id: " + dto.proposedById()));

        Game game = GameMapper.toGameEntity(dto, proposedBy);
        Game savedGame = gameRepository.save(game);

        GameResponseDTO requestDTO = GameMapper.toResponseDto(savedGame);
        return ResponseEntity.ok(requestDTO);
    }
//        public ResponseEntity<Game> createGame (Game newGame){
//            Game savedGame = gameRepository.save(newGame);
//            return ResponseEntity.ok(savedGame);
//        }

        // delete/cancel a game
        @DeleteMapping("games{id}")
        public ResponseEntity<Game> deleteGame (@PathVariable long id) { //requestParam
            Optional<Game> optionalGame = gameRepository.findById(id);

            if (optionalGame.isPresent()) {
                gameRepository.deleteById(id);
                return new ResponseEntity("Spiel mit der " + id + " wurde erfolgreich gelöscht", HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity("Spiel mit der " + id + " nicht gefunden", HttpStatus.NOT_FOUND);

            }
        }

        //updateGame
        public ResponseEntity<Game> updateGame (@RequestBody Game updateGame, @PathVariable long id) {
        Optional<Game> existingGameOpt  = gameRepository.findById(id);

        if (existingGameOpt.isPresent()) {
            updateGame.setId(id); // make sure that the id is set
            Game savedGame = gameRepository.save(updateGame);
            return ResponseEntity.ok(savedGame);
        }

            return ResponseEntity.notFound().build();


        }

        //getAllGamesInDB
        public ResponseEntity<Iterable<Game>> getAllGames() {
            Iterable<Game> allGamesInDb = gameRepository.findAll();
            List<Game> gameList = StreamSupport.stream(allGamesInDb.spliterator(), false).toList(); //converts iterable to list

              if (gameList.isEmpty()) {
                  return ResponseEntity.noContent().build();
              }
            return ResponseEntity.ok(gameList);


        }

      //get games by the number of players
    public List<Game> getGamesByPlayerCount(int playerCount) {
        return gameRepository.findByMinPlayersLessThanEqualAndMaxPlayersGreaterThanEqual(playerCount, playerCount);

    }

    //suggest Game
    public Game proposedGame(Long playerId, String gameName) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

                Game game = new Game();
                game.setName(gameName);

                player.addGame(game);

                playerRepository.save(player);

                return game;
    }




}


