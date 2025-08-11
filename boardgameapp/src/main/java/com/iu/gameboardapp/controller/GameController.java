package com.iu.gameboardapp.controller;

import com.iu.gameboardapp.model.Game;
import com.iu.gameboardapp.model.Player;
import com.iu.gameboardapp.service.GameService;
import dtos.GameRequestDTO;
import dtos.GameResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GameController {


    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }


    @GetMapping("/games/{id}") // get games
    public ResponseEntity<Game> getGames(@PathVariable long id) {
        return gameService.getGameById(id);
    }

    @GetMapping("/allGames")
    public ResponseEntity<Iterable<Game>> getAllGames() {
        return gameService.getAllGames();
    }

    @GetMapping("/games/by-player-count")
    public ResponseEntity<List<Game>> getPlayerByCount(@RequestParam int playerCount) {
        List<Game> games = gameService.getGamesByPlayerCount(playerCount);

        if(games.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(games);
    }

    @PostMapping("/games/create")
    public ResponseEntity<GameResponseDTO> createGame(@RequestBody GameRequestDTO dto) {
        return gameService.createGame(dto);
    }

    @DeleteMapping("/games/delete")
    public ResponseEntity<Game> deleteGame(@RequestParam (value = "id") long id) {
        return gameService.deleteGame(id);

    }

    @PutMapping("/games/{id}")
    public ResponseEntity<Game> updateGame(@RequestBody Game updateGame, @PathVariable long id) {
        return gameService.updateGame(updateGame, id);
    }

    //players can propose games
    @PostMapping("/games/proposed")
    public ResponseEntity<Game> proposedGame(
            @RequestParam Long playerId,
            @RequestParam String gameName) {

        Game proposedGame = gameService.proposedGame(playerId, gameName);
        return ResponseEntity.ok(proposedGame);
    }


}