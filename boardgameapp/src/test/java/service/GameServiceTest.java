package service;

import com.iu.gameboardapp.model.Game;
import com.iu.gameboardapp.model.enums.DifficultyLevel;
import com.iu.gameboardapp.repository.GameRepository;
import com.iu.gameboardapp.service.GameService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetGameById_Found() {
        Game game = new Game(1L, "Schach", 2, 2, 60, DifficultyLevel.MEDIUM);
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        ResponseEntity<Game> response = gameService.getGameById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(game, response.getBody());
    }

    @Test
    void testGetGameById_NotFound() {
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Game> response = gameService.getGameById(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreateGame() {
        Game newGame = new Game(1L, "Monopoly", 2, 6, 120, DifficultyLevel.EASY);
        when(gameRepository.save(newGame)).thenReturn(newGame);

        ResponseEntity<Game> response = gameService.createGame(newGame);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(newGame, response.getBody());
    }

    @Test
    void testDeleteGame_Found() {
        Game game = new Game(1L, "Risiko", 2, 6, 180, DifficultyLevel.HARD);
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        ResponseEntity<Game> response = gameService.deleteGame(1L);

        verify(gameRepository, times(1)).deleteById(1L);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testDeleteGame_NotFound() {
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Game> response = gameService.deleteGame(1L);

        verify(gameRepository, never()).deleteById(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateGame_Found() {
        Game existingGame = new Game(1L, "Uno", 2, 10, 30, DifficultyLevel.EASY);
        Game updateGame = new Game(null, "Uno Deluxe", 2, 10, 35, DifficultyLevel.MEDIUM);

        when(gameRepository.findById(1L)).thenReturn(Optional.of(existingGame));
        when(gameRepository.save(any(Game.class))).thenReturn(updateGame);

        ResponseEntity<Game> response = gameService.updateGame(updateGame, 1L);

        assertEquals(200, response.getStatusCodeValue());
        verify(gameRepository).save(updateGame);
        assertEquals(updateGame, response.getBody());
    }

    @Test
    void testUpdateGame_NotFound() {
        Game updateGame = new Game(null, "Uno Deluxe", 2, 10, 35, DifficultyLevel.MEDIUM);
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Game> response = gameService.updateGame(updateGame, 1L);

        assertEquals(404, response.getStatusCodeValue());
        verify(gameRepository, never()).save(any(Game.class));
    }

    @Test
    void testGetAllGames_NoGames() {
        when(gameRepository.findAll()).thenReturn(List.of());

        ResponseEntity<Iterable<Game>> response = gameService.getAllGames();

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testGetAllGames_WithGames() {
        List<Game> games = List.of(
                new Game(1L, "Catan", 3, 4, 90, DifficultyLevel.MEDIUM)
        );
        when(gameRepository.findAll()).thenReturn(games);

        ResponseEntity<Iterable<Game>> response = gameService.getAllGames();

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().iterator().hasNext());
    }

    @Test
    void testGetGamesByPlayerCount() {
        List<Game> games = List.of(new Game(1L, "Twister", 2, 4, 20, DifficultyLevel.EASY));
        when(gameRepository.findByMinPlayersLessThanEqualAndMaxPlayersGreaterThanEqual(3, 3))
                .thenReturn(games);

        List<Game> result = gameService.getGamesByPlayerCount(3);

        assertEquals(1, result.size());
        assertEquals("Twister", result.get(0).getName());
    }

}
