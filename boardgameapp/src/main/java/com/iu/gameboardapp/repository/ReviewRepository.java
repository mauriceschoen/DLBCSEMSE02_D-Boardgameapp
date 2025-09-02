package com.iu.gameboardapp.repository;

import com.iu.gameboardapp.model.Message;
import com.iu.gameboardapp.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAll();
    Optional<Review> findById(Long id);
    Review save(Review review);
    void deleteById(Long id);

    List<Review> findByGameNightId(Long gameNightId);
    List<Review> findByPlayerId(Long playerId);
    Optional<Review> findByPlayerIdAndGameNightId(Long playerId, Long gameNightId);
}
