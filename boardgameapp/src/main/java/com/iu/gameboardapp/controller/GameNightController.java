package com.iu.gameboardapp.controller;

import com.iu.gameboardapp.repository.GameNightRepository;
import com.iu.gameboardapp.service.GameNightService;
import dtos.GameNightResponseDTO;
import dtos.PlayerResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.iu.gameboardapp.model.enums.GameNightStatus;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GameNightController {

    private final GameNightService gameNightService;
    private final GameNightRepository gameNightRepository;

    public GameNightController(GameNightService gameNightService, GameNightRepository gameNightRepository) {
        this.gameNightService = gameNightService;
        this.gameNightRepository = gameNightRepository;
    }

    @PostMapping("/createGameNight")
    public ResponseEntity<GameNightResponseDTO> createGameNight(@RequestBody GameNightResponseDTO newGameNight) {
        try {
            GameNightResponseDTO savedGameNight = gameNightService.createGameNight(newGameNight);
            return ResponseEntity.ok(savedGameNight);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Return Bad Request if data is invalid
        } catch (Exception e) {
            // General error case
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // get upcoming game Nights
    @GetMapping("/upcoming")
    public ResponseEntity<List<GameNightResponseDTO>> getUpcomingGameNight() {
        List<GameNightResponseDTO> gameNights = gameNightService.getUpcomingGameNights();
        if (gameNights.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(gameNights);
    }


    // get Game Night by id
    @GetMapping("/gameNight/{id}")
    public ResponseEntity<GameNightResponseDTO> getGameNightById(@PathVariable Long id) {
        GameNightResponseDTO dto = gameNightService.getGameNightById(id);
        return ResponseEntity.ok(dto);
    }

    // rotate host, next in the list
    @PutMapping("/gameNight/{id}/rotate-host")
    public ResponseEntity<PlayerResponseDTO> rotateHost(@PathVariable Long id) {
        PlayerResponseDTO newHost = gameNightService.rotateHost(id);
        return ResponseEntity.ok(newHost);
    }



    // update gameNight Status
    @PutMapping("/gameNight/{id}/status")
    public ResponseEntity<GameNightResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam GameNightStatus status
    ) {
        GameNightResponseDTO updated = gameNightService.updateGameNightStatus(id, status);
        return ResponseEntity.ok(updated);
    }


    // schedule next game night
    @PostMapping("/gameNight/schedule-next")
    public ResponseEntity<GameNightResponseDTO> scheduleNext() {
        GameNightResponseDTO next = gameNightService.scheduleNextGameNight();
        return ResponseEntity.ok(next);
    }


    // send Game Night Reminders
    @PostMapping("/gameNight/{id}/reminders")
    public ResponseEntity<Void> sendReminders(@PathVariable Long id) {
        gameNightService.sendGameNightReminders(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{gameNightId}/games/{gameId}")
    public ResponseEntity <Void> addGameToGameNight(
            @PathVariable Long gameNightId,
            @PathVariable Long gameId) {
        gameNightService.addGameToGameNight(gameNightId, gameId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{gameNight}/games/{gameId}")
    public ResponseEntity <Void> deleteGameFromGameNight(
            @PathVariable Long gameNightId,
            @PathVariable Long gameId) {
        gameNightService.removeGameFromGameNight(gameNightId, gameId);
        return ResponseEntity.ok().build();
    }

}

