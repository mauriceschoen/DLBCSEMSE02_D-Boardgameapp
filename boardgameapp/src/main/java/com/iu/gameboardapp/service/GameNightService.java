package com.iu.gameboardapp.service;

import com.iu.gameboardapp.model.Game;
import com.iu.gameboardapp.model.GameNight;
import com.iu.gameboardapp.model.Player;
import com.iu.gameboardapp.repository.GameNightRepository;
import com.iu.gameboardapp.repository.GameRepository;
import com.iu.gameboardapp.repository.PlayerRepository;
import dtos.GameNightResponseDTO;
import dtos.PlayerResponseDTO;
import mapper.GameNightMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.iu.gameboardapp.model.enums.GameNightStatus;


@Service
public class GameNightService {


    private final GameNightRepository gameNightRepository;
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;



    public GameNightService(
            GameNightRepository gameNightRepository,
            PlayerRepository playerRepository,
            GameRepository gameRepository) {
        this.gameNightRepository = gameNightRepository;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;

    }

    // create a game night
    public GameNightResponseDTO createGameNight(GameNightResponseDTO newGameNight) {

        //load host
        Player host = playerRepository.findById(newGameNight.hostId())
                .orElseThrow(() -> new IllegalArgumentException(("Host not found")));

        //load participants
        List<Player> participants = newGameNight.participantIds().stream()
                .map(id -> playerRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Participant with id " + id + " not found")))
                .toList();

        // build gameNight
        GameNight gameNight = new GameNight();
        gameNight.setDate(newGameNight.date());
        gameNight.setLocation(newGameNight.location());
        gameNight.setStatus(newGameNight.status());
        gameNight.setMaxParticipants(newGameNight.maxParticipants());
        gameNight.setHost(host);
        gameNight.setParticipants(participants);

        // save
        GameNight saved = gameNightRepository.save(gameNight);

        // Maintain inverse side
        for (Player participant : participants) {
            participant.getGameNights().add(saved);
            playerRepository.save(participant);
        }

        return GameNightMapper.toDto(saved);

//        //Check host and load from DB
//        if (newGameNight.getHost() == null || newGameNight.getHost().getId() == 0) {
//            throw new IllegalArgumentException("Host must be specified with a valid ID");
//        }
//
//        Player host = playerRepository.findById(newGameNight.getHost().getId())
//                .orElseThrow(() -> new IllegalArgumentException("Host not found"));
//        newGameNight.setHost(host);
//
//        // Check & load participant IDs
//        List<Player> resolvedParticipants = new ArrayList<>();
//        for (Player p : newGameNight.getParticipants()) {
//            Player participant = playerRepository.findById(p.getId())
//                    .orElseThrow(() -> new IllegalArgumentException("Participant with id " + p.getId() + " not found"));
//            resolvedParticipants.add(participant);
//        }
//
//        //Set inverse side (GameNight knows participants)
//        newGameNight.setParticipants(resolvedParticipants);
//
//        // Save GameNight -> has host & participant list
//        GameNightResponseDTO savedGameNight = gameNightRepository.save(newGameNight);
//
//        //  Maintain owner page -> Every player knows the new GameNight
//        for (Player participant : resolvedParticipants) {
//            participant.getGameNights().add(savedGameNight);
//            playerRepository.save(participant);
//        }
//
//        return savedGameNight;
    }

    public List<GameNightResponseDTO> getUpcomingGameNights() {
        return gameNightRepository.findByDateAfter(LocalDateTime.now())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    //get upcoming Game Nights
    private GameNightResponseDTO convertToDTO(GameNight gameNight) {
        return GameNightMapper.toDto(gameNight);
    }

    // get Game Night by id
    public GameNightResponseDTO getGameNightById(Long id) {
        GameNight gameNight = gameNightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GameNight not found with ID: " + id));

        return convertToDTO(gameNight);
    }





    // rotate to next host
    public PlayerResponseDTO rotateHost(Long gameNightId) {
        GameNight gameNight = gameNightRepository.findById(gameNightId)
                .orElseThrow(() -> new RuntimeException("GameNight not found"));

        List<Player> participants = gameNight.getParticipants();
        if (participants.isEmpty()) {
            throw new RuntimeException("No participants to rotate host");
        }

        Player currentHost = gameNight.getHost();
        int currentIndex = participants.indexOf(currentHost);

        // Fallback: if the current host is not in participants
        if (currentIndex == -1) {
            currentIndex = -1; // so that nextIndex starts at 0
        }

        int nextIndex = (currentIndex + 1) % participants.size();
        Player nextHost = participants.get(nextIndex);

        gameNight.setHost(nextHost);
        gameNightRepository.save(gameNight);

        //Mapping right here
        return mapToPlayerResponseDTO(nextHost);
    }

    private PlayerResponseDTO mapToPlayerResponseDTO(Player player) {
        return new PlayerResponseDTO(
                player.getId(),
                player.getName(),
                player.getEmail(),
                player.getPhoneNumber(),
                player.getFoodPreference()
        );
    }


    // update the game status
    public GameNightResponseDTO updateGameNightStatus(Long id, GameNightStatus status) {
        GameNight gameNight = gameNightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GameNight not found"));

        gameNight.setStatus(status);
        gameNightRepository.save(gameNight);

        return convertToDTO(gameNight); // DTO, so no endless loop!
    }



    //schedule next game night
    public GameNightResponseDTO scheduleNextGameNight() {
        // get last game night
        GameNight lastGameNight = gameNightRepository.findTopByOrderByDateDesc()
                .orElseThrow(() -> new RuntimeException("No previous game night"));

        GameNight newGameNight = new GameNight();
        newGameNight.setDate(lastGameNight.getDate().plusWeeks(1)); // one week later
        newGameNight.setLocation(lastGameNight.getLocation());
        newGameNight.setStatus(GameNightStatus.PLANNED);
        newGameNight.setMaxParticipants(lastGameNight.getMaxParticipants());
        newGameNight.setHost(lastGameNight.getHost());
        newGameNight.setParticipants(new ArrayList<>(lastGameNight.getParticipants()));

        gameNightRepository.save(newGameNight);

        return convertToDTO(newGameNight);
    }


    // sent gameNight Reminder
    public void sendGameNightReminders(Long gameNightId) {
        GameNight gameNight = gameNightRepository.findById(gameNightId)
                .orElseThrow(() -> new RuntimeException("GameNight not found"));

        for (Player participant : gameNight.getParticipants()) {
            // send to mail
            System.out.println("Reminder sent to: " + participant.getEmail());
        }
    }

    // add a game to a gameNight
    public void addGameToGameNight(Long gameNightId, Long gameId) {
        GameNight gameNight = gameNightRepository.findById(gameNightId)
                .orElseThrow(() -> new RuntimeException("GameNight not found"));

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        gameNight.addGame(game);

        gameNightRepository.save(gameNight);
    }

    public void removeGameFromGameNight(Long gameNightId, Long gameId) {
        GameNight gameNight = gameNightRepository.findById(gameNightId)
                .orElseThrow(() -> new RuntimeException("GameNight not found"));

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        gameNight.removeGame(game);

        gameNightRepository.save(gameNight);
    }

}
