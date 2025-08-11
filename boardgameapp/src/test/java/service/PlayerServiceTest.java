package service;

import com.iu.gameboardapp.model.Player;
import com.iu.gameboardapp.model.enums.FoodType;
import com.iu.gameboardapp.repository.PlayerRepository;
import com.iu.gameboardapp.service.PlayerSerivce;
import dtos.PlayerResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerServiceTest {

    private PlayerRepository playerRepository;
    private PlayerSerivce playerService;

    @BeforeEach
    void setUp() {
        playerRepository = mock(PlayerRepository.class);
        playerService = new PlayerSerivce(playerRepository);
    }

    @Test
    void getAllPlayers_shouldReturnList() {
        Player p1 = new Player(1L, "A", "a@mail.com", "pw", FoodType.GREEK, true, "123");
        Player p2 = new Player(2L, "B", "b@mail.com", "pw", FoodType.INDIAN, true, "456");

        when(playerRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<PlayerResponseDTO> result = playerService.getAllPlayers();

        PlayerResponseDTO expectedDto1 = new PlayerResponseDTO(p1.getId(), p1.getName(), p1.getEmail(), p1.getPhoneNumber(), p1.getFoodPreference());
        PlayerResponseDTO expectedDto2 = new PlayerResponseDTO(p2.getId(), p2.getName(), p2.getEmail(), p2.getPhoneNumber(), p2.getFoodPreference());

        assertThat(result)
                .hasSize(2)
                .containsExactly(expectedDto1, expectedDto2);

        verify(playerRepository).findAll();
    }


    @Test
    void getPlayerById_shouldReturnPlayer() {
        Player p = new Player(1L, "A", "a@mail.com", "pw", FoodType.GREEK, true, "123");
        when(playerRepository.findById(1L)).thenReturn(Optional.of(p));

        var response = playerService.getPlayerById(1L);

        assertThat(response.getBody()).isEqualTo(p);
        verify(playerRepository).findById(1L);
    }

    @Test
    void getPlayerById_shouldReturnNotFound() {
        when(playerRepository.findById(99L)).thenReturn(Optional.empty());

        var response = playerService.getPlayerById(99L);

        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
        verify(playerRepository).findById(99L);
    }

    @Test
    void createPlayer_shouldSavePlayer() {
        Player p = new Player(0L, "New", "new@mail.com", "pw", FoodType.THAI, true, "123");

        when(playerRepository.save(p)).thenReturn(p);

        Player saved = playerService.createPlayer(p);

        assertThat(saved).isEqualTo(p);
        verify(playerRepository).save(p);
    }

    @Test
    void updatePlayer_shouldUpdateIfExists() {
        Player p = new Player(1L, "A", "a@mail.com", "pw", FoodType.PIZZA, true, "123");
        Player updated = new Player(0L, "Updated", "upd@mail.com", "pw2", FoodType.TURKISH, false, "999");

        when(playerRepository.findById(1L)).thenReturn(Optional.of(p));
        when(playerRepository.save(updated)).thenReturn(updated);

        Player result = playerService.updatePlayer(updated, 1L);

        assertThat(result).isEqualTo(updated);
        assertThat(result.getId()).isEqualTo(1L);
        verify(playerRepository).findById(1L);
        verify(playerRepository).save(updated);
    }

    @Test
    void updatePlayer_shouldThrowIfNotExists() {
        Player updated = new Player();

        when(playerRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> playerService.updatePlayer(updated, 2L))
                .isInstanceOf(EntityNotFoundException.class);

        verify(playerRepository).findById(2L);
        verify(playerRepository, never()).save(any());
    }

    @Test
    void deletePlayer_shouldDeleteIfExists() {
        when(playerRepository.existsById(1L)).thenReturn(true);

        playerService.deletePlayer(1L);

        verify(playerRepository).existsById(1L);
        verify(playerRepository).deleteById(1L);
    }

    @Test
    void deletePlayer_shouldThrowIfNotExists() {
        when(playerRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> playerService.deletePlayer(1L))
                .isInstanceOf(EntityNotFoundException.class);

        verify(playerRepository).existsById(1L);
        verify(playerRepository, never()).deleteById(anyLong());
    }

    @Test
    void updateFoodPreference_shouldUpdate() {
        Player p = new Player(1L, "A", "a@mail.com", "pw", FoodType.ITALIAN, true, "123");

        when(playerRepository.findById(1L)).thenReturn(Optional.of(p));
        when(playerRepository.save(any(Player.class))).thenReturn(p);

        PlayerResponseDTO updated = playerService.updateFoodPreference(1L, FoodType.ITALIAN);

        assertThat(updated.getFoodPreference()).isEqualTo(FoodType.ITALIAN);
        verify(playerRepository).findById(1L);
        verify(playerRepository).save(p);
    }

    @Test
    void getPlayersByFoodPreference_shouldReturnList() {
        List<Player> list = List.of(new Player(1L, "A", "a@mail.com", "pw", FoodType.GREEK, true, "123"));
        when(playerRepository.findByFoodPreference(FoodType.GREEK)).thenReturn(list);

        List<PlayerResponseDTO> result = playerService.getPlayersByFoodPreference(FoodType.GREEK);

        assertThat(result).hasSize(1);
        verify(playerRepository).findByFoodPreference(FoodType.GREEK);
    }

    @Test
    void getActivePlayer_shouldReturnActivePlayers() {
        List<Player> list = List.of(new Player(1L, "A", "a@mail.com", "pw", FoodType.GREEK, true, "123"));
        when(playerRepository.findByActiveTrue()).thenReturn(list);

        List<Player> result = playerService.getActivePlayer();

        assertThat(result).hasSize(1);
        verify(playerRepository).findByActiveTrue();
    }
}
