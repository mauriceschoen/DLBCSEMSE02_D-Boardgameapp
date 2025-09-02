package mapper;

import com.iu.gameboardapp.model.GameNight;
import com.iu.gameboardapp.model.Player;
import com.iu.gameboardapp.model.Review;
import dtos.ReviewRequestDTO;
import dtos.ReviewResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public static Review toEntity(ReviewRequestDTO dto, Player player, GameNight gameNight) {
        Review review = new Review();
        review.setId(dto.id());
        review.setPlayer(player);
        review.setGameNight(gameNight);
        review.setHostRating(dto.hostRating());
        review.setFoodRating(dto.foodRating());
        review.setFunRating(dto.funRating());
        return review;
    }

    public static ReviewResponseDTO toResponseDTO(Review review) {
        return new ReviewResponseDTO(
                review.getId(),
                review.getPlayer() != null ? review.getPlayer().getName() : null,
                review.getHostRating(),
                review.getFoodRating(),
                review.getFunRating()
        );
    }
}
