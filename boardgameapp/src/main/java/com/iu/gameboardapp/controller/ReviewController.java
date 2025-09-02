package com.iu.gameboardapp.controller;

import com.iu.gameboardapp.service.ReviewService;
import dtos.ReviewRequestDTO;
import dtos.ReviewResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewResponseDTO> addReview(@RequestBody ReviewRequestDTO dto) {
        return ResponseEntity.ok(reviewService.addReview(dto));
    }

    @GetMapping("/gamenight/{gameNightId}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsForGameNight(@PathVariable Long gameNightId) {
        return ResponseEntity.ok(reviewService.getReviewsForGameNight(gameNightId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
