package com.iu.gameboardapp.repository;

import com.iu.gameboardapp.model.GameNight;
import com.iu.gameboardapp.model.enums.GameNightStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GameNightRepository extends JpaRepository<GameNight, Long> {

    Optional<GameNight> findById(Long id);
    GameNight save(GameNight save);
    void deleteById(long id);
    List<GameNight> findByStatus(GameNightStatus status);
    List<GameNight> findByDateAfter(LocalDateTime now);
    List<GameNight> findByDateBefore(LocalDateTime now);
    Optional<GameNight> findByHostId(Long id);
    Optional<GameNight> findTopByOrderByDateDesc();
    Optional<GameNight> findFirstByDateAfterOrderByDateAsc(LocalDateTime now);

}
