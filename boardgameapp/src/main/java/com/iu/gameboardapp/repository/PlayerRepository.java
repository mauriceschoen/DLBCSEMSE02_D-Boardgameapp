package com.iu.gameboardapp.repository;

import com.iu.gameboardapp.model.GameNight;
import com.iu.gameboardapp.model.Player;
import com.iu.gameboardapp.model.enums;
import org.springframework.data.jpa.repository.JpaRepository;
import com.iu.gameboardapp.model.enums.FoodType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findById(Long id);
    Player save(Player save);
    void deleteById(long id);
    Optional<Player> findByEmail(String email);
    List<Player> findByFoodPreference(FoodType preference);
    List<Player> findByActiveTrue();
    boolean existsByEmail(String Email);

}
