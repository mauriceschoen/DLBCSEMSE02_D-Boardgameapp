package com.iu.gameboardapp.controller;

import com.iu.gameboardapp.model.Player;
import com.iu.gameboardapp.service.PlayerSerivce;
import dtos.PlayerResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.iu.gameboardapp.model.enums.FoodType;


import java.util.List;

@RestController
@RequestMapping("/api")
public class PlayerController {

    private final PlayerSerivce playerSerivce;


    public PlayerController(PlayerSerivce playerSerivce) {
        this.playerSerivce = playerSerivce;

    }

    // get all players
    @GetMapping("/players")
    public ResponseEntity<List<PlayerResponseDTO>> getAllPlayer() {
        List<PlayerResponseDTO> players = playerSerivce.getAllPlayers();
        if (players.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(players);
    }

    @GetMapping("/player{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable long id) {
        return playerSerivce.getPlayerById(id);
    }

    @PostMapping("/createPlayer")
    public ResponseEntity<Player> createPlayer(@RequestBody Player newPlayer) {
        Player savedPlayer = playerSerivce.createPlayer(newPlayer);
        return ResponseEntity.ok(savedPlayer);
    }

    @PutMapping("/player/{id}")
    public ResponseEntity<Player> updatePlayer(@RequestBody Player updatedPlayer, @PathVariable long id) {
        Player savedPlayer = playerSerivce.updatePlayer(updatedPlayer, id);
        return ResponseEntity.ok(savedPlayer);
    }


    @DeleteMapping("/player/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable long id) {
        try {
            playerSerivce.deletePlayer(id);
            return ResponseEntity.noContent().build(); // 204 No Content => for successfully deletion
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    //Update Food Preference
    @PutMapping("/{id}/food-preference")
    public ResponseEntity<PlayerResponseDTO> updateFoodPreference(
            @PathVariable Long id,
            @RequestParam FoodType preference) {
        try {
            PlayerResponseDTO updated = playerSerivce.updateFoodPreference(id, preference);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }


    // Get player by foodPreference
    @GetMapping("/by-food-preference")
    public ResponseEntity<List<PlayerResponseDTO>> getPlayersByFoodPreference(
            @RequestParam FoodType preference) {
        List<PlayerResponseDTO> players = playerSerivce.getPlayersByFoodPreference(preference);

        if (players.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(players);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Player>> getActivePlayers() {
        List<Player> activePlayers = playerSerivce.getActivePlayer();

        if (activePlayers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(activePlayers);
    }



}
