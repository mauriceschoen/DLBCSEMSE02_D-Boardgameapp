package mapper;

import com.iu.gameboardapp.model.Player;
import com.iu.gameboardapp.model.Vote;
import dtos.VoteResponseDTO;
import dtos.VoteRequestDTO;

public class VoteMapper {

    public static VoteRequestDTO toVoteDto(Vote vote) {
        Long playerId = null;
        if (vote.getPlayer() != null) {
            playerId = vote.getPlayer().getId();
        }

        return new VoteRequestDTO(
                vote.getId(),
                playerId,
                vote.getGame().getId(),
                vote.getGameNight().getId(),
                vote.getVoteValue(),
                vote.getVotedAt()

        );
    }


    public static Vote toVoteEntity(VoteRequestDTO dto) {
        Vote vote = new Vote();
        vote.setId(dto.id());
        vote.setVoteValue(dto.voteValue());
        vote.setVotedAt(dto.votedAt());
        return vote;

    }

    public static VoteResponseDTO toResponseDto(Vote vote) {
        Long playerId = null;
        if (vote.getPlayer() != null) {
            playerId = vote.getPlayer().getId();
        }

        return new VoteResponseDTO(
                vote.getId(),
                playerId,
                vote.getGame().getId(),
                vote.getGameNight().getId(),
                vote.getVoteValue(),
                vote.getVotedAt()

        );
    }
}



