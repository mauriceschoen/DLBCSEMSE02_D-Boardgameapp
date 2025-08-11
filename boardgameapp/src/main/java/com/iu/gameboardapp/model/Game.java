package com.iu.gameboardapp.model;
import jakarta.persistence.*;
import com.iu.gameboardapp.model.enums.DifficultyLevel; //import enums Class
import jakarta.validation.constraints.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
public class Game {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @Size(max = 500)
    private String description;
    @NotNull
    @Min(1)
    private Integer minPlayers;
    @NotNull
    @Min(1)
    private Integer maxPlayers;
    @NotNull
    @Min(1)
    private Integer duration;
    @NotNull
    @Enumerated(EnumType.STRING) // saves enum as String in the db
    private DifficultyLevel difficulty;


    @ManyToMany(mappedBy = "games")
    private Set<GameNight> gameNights = new HashSet<>();


    // A game can be chosen in many votes
    @OneToMany(mappedBy = "game")
    private List<Vote> votes;

    // helper method association Vote - Game
    public void addVote(Vote vote) {
        votes.add(vote);
        vote.setGame(this);
    }


    // A game is suggested by a player
    @ManyToOne
    @JoinColumn(name = "proposed_by_id")
    private Player proposedBy;



    public Game(String name, Integer minPlayers, Integer maxPlayers, Integer duration, enums.DifficultyLevel difficulty, Player proposedBy) {
        this.name = name;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.duration = duration;
        this.difficulty = difficulty;
        this.proposedBy = proposedBy;
    }

    public Game() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.length() <= 3) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(Integer minPlayers) {
        this.minPlayers = minPlayers;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(Integer maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public enums.DifficultyLevel getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(enums.DifficultyLevel difficulty) {
        this.difficulty = difficulty;
    }

    public Set<GameNight> getGameNights() {
        return gameNights;
    }

    public void setGameNights(Set<GameNight> gameNights) {
        this.gameNights = gameNights;
    }

    //Helper Method
    public void addGameNight(GameNight gameNight) {
        gameNights.add(gameNight);
        gameNight.getGames().add(this);
    }

    public void removeGameNight(GameNight gameNight) {
        gameNights.remove(gameNight);
        gameNight.getGames().remove(this);
    }




    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public Player getProposedBy() {
        return proposedBy;
    }

    public void setProposedBy(Player proposedBy) {
        this.proposedBy = proposedBy;
    }

}
