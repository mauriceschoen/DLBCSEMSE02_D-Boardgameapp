package com.iu.gameboardapp.service;

import com.iu.gameboardapp.model.GameNight;
import com.iu.gameboardapp.model.Player;
import com.iu.gameboardapp.model.Review;
import com.iu.gameboardapp.repository.GameNightRepository;
import com.iu.gameboardapp.repository.PlayerRepository;
import com.iu.gameboardapp.repository.ReviewRepository;
import dtos.ReviewRequestDTO;
import dtos.ReviewResponseDTO;
import mapper.ReviewMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PlayerRepository playerRepository;
    private final GameNightRepository gameNightRepository;

    public ReviewService(ReviewRepository reviewRepository, PlayerRepository playerRepository, GameNightRepository gameNightRepository) {
        this.reviewRepository = reviewRepository;
        this.playerRepository = playerRepository;
        this.gameNightRepository = gameNightRepository;
    }

    public ReviewResponseDTO addReview(ReviewRequestDTO dto) {
        Player player = playerRepository.findById(dto.playerId())
                .orElseThrow(() -> new RuntimeException("Player not found"));
        GameNight gameNight = gameNightRepository.findById(dto.gameNightId())
                .orElseThrow(() -> new RuntimeException("GameNight not found"));

        Review review = ReviewMapper.toEntity(dto, player, gameNight);
        Review saved = reviewRepository.save(review);

        return ReviewMapper.toResponseDTO(saved);
    }

    public List<ReviewResponseDTO> getReviewsForGameNight(Long gameNightId) {
        return reviewRepository.findByGameNightId(gameNightId)
                .stream()
                .map(ReviewMapper::toResponseDTO)
                .toList();
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
