package com.iu.gameboardapp.repository;

import com.iu.gameboardapp.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findById(Long id);
    Vote save(Vote save);
    void deleteById(Long id);
    List<Vote> findByGameProposedById(Long proposedById);
    List<Vote> findByPlayerId(Long playerId);
    Optional<Vote> findByPlayerIdAndGameProposedById(Long playerId, Long proposedById);
    List<Vote> findByGameNightId(Long gameNightId);
}
