package com.iu.gameboardapp.model;

import jakarta.persistence.*;
import com.iu.gameboardapp.model.enums.VoteType;

import java.time.LocalDateTime;

@Entity
public class Vote {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private VoteType voteValue;
    private LocalDateTime votedAt;

    //The player who has voted
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    //To which GameNight belongs the vote
    @ManyToOne
    @JoinColumn(name = "game_night_id")
    private GameNight gameNight;

    //For which game has been voted
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;


    public Vote(Player player, Game game,
                GameNight gameNight,
                VoteType voteValue,
                LocalDateTime votedAt
                ) {
        this.player = player;
        this.game = game;
        this.gameNight = gameNight;
        this.voteValue = voteValue;
        this.votedAt = votedAt;
    }



    public Vote() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public VoteType getVoteValue() {
        return voteValue;
    }

    public void setVoteValue(VoteType voteValue) {
        this.voteValue = voteValue;
    }

    public LocalDateTime getVotedAt() {
        return votedAt;
    }

    public void setVotedAt(LocalDateTime votedAt) {
        this.votedAt = votedAt;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public GameNight getGameNight() {
        return gameNight;
    }

    public void setGameNight(GameNight gameNight) {
        this.gameNight = gameNight;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

}
