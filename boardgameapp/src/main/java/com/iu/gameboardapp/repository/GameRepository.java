package com.iu.gameboardapp.repository;

import com.iu.gameboardapp.model.Game;
import com.iu.gameboardapp.model.enums;
import org.springframework.data.jpa.repository.JpaRepository;

import com.iu.gameboardapp.model.enums.DifficultyLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {



    Optional<Game> findById(Long id);
    Game save(Game save);
    void deleteById(long id);
     Optional<Game> findByName(String name);
    List<Game> findByMinPlayersLessThanEqualAndMaxPlayersGreaterThanEqual(int playerCount, int playerCountAgain);

    List<Game> findByDifficulty(DifficultyLevel difficulty);


}