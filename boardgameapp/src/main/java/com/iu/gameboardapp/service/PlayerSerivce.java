package com.iu.gameboardapp.service;

import com.iu.gameboardapp.model.Player;
import com.iu.gameboardapp.repository.PlayerRepository;
import dtos.PlayerResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import mapper.PlayerMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.iu.gameboardapp.model.enums.FoodType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerSerivce {


    private final PlayerRepository playerRepository;



    public PlayerSerivce(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;

    }


    // get all players
   public List<PlayerResponseDTO> getAllPlayers() {
        return playerRepository.findAll()
                .stream()
                .map(PlayerMapper::toDto) //Map player mapper Object into the player dto (toDto Method in mapper)
               .collect(Collectors.toList());


//        Iterable<Player> allPlayersinDb = playerRepository.findAll();
//        List<Player> playerList = StreamSupport.stream(allPlayersinDb.spliterator(), false).toList();
   }

    // get player by id
   public ResponseEntity<Player> getPlayerById(@RequestParam(value= "id") Long id) {
        return playerRepository.findById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    public Player createPlayer(Player newPlayer) {
        return playerRepository.save(newPlayer);
    }



    public Player updatePlayer(Player updatedPlayer, long id) {
        Optional<Player> existingPlayerOpt = playerRepository.findById(id);

        if (existingPlayerOpt.isPresent()) {
            updatedPlayer.setId(id);
            return playerRepository.save(updatedPlayer);
        } else {
            throw new EntityNotFoundException("Player not found with ID: " + id);
        }
    }



    public void deletePlayer(long id) {
        if (!playerRepository.existsById(id)) {
            throw new EntityNotFoundException("Spieler mit ID " + id + " nicht gefunden");
        }
        playerRepository.deleteById(id);
    }

    // update Food Preference
    public PlayerResponseDTO updateFoodPreference(Long id, FoodType preference) {
        Optional<Player> playerOpt = playerRepository.findById(id);

        if (playerOpt.isPresent()) {
            Player player = playerOpt.get();
            player.setFoodPreference(preference);
            Player updatedPlayer = playerRepository.save(player);
            return PlayerMapper.toDto(updatedPlayer);
        } else {
            throw new RuntimeException("Player not found with ID: " + id);
        }
    }



    // get player with certain Food Preference
    public List<PlayerResponseDTO> getPlayersByFoodPreference(FoodType preference) {
        return playerRepository.findByFoodPreference(preference)
                .stream()
                .map(PlayerMapper::toDto)
                .collect(Collectors.toList());
    }



    // get all active player
        public List<Player> getActivePlayer() {
            return playerRepository.findByActiveTrue();
        }
}



