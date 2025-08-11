package com.iu.gameboardapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.iu.gameboardapp.model.enums.GameNightStatus;
import jakarta.validation.constraints.NotNull;


@Entity
public class GameNight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime date;
    private String location;
    private GameNightStatus status;
    @NotNull
    private Integer maxParticipants;
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "gameNights")
  //  @JsonManagedReference(value = "player-gameNight")
    private List<Player> participants = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "host_id") // Foreign Key in GameNight-Table
    //@JsonManagedReference(value = "player-hostedNights")
    private Player host;


    @ManyToMany
    @JoinTable(
            name = "game_night_games",
            joinColumns = @JoinColumn(name = "game_night_id"),
            inverseJoinColumns =  @JoinColumn(name = "game_id")
    )
    private Set<Game> games = new HashSet<>();



    //One GameNight has many votes
    @OneToMany(mappedBy = "gameNight")
    private List<Vote> votes;

    // helper method vor association GameNight - Vote
    public void addVote(Vote vote) {
        votes.add(vote);
        vote.setGameNight(this);
    }


    public GameNight(Long id, LocalDateTime date, String location, GameNightStatus status, Integer maxParticipants, LocalDateTime createdAt) {
        this.date = date;
        this.location = location;
        this.status = status;
        this.maxParticipants = maxParticipants;
        this.createdAt = createdAt;
    }

    public GameNight() {

    }


    public List<Player> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Player> participants) {
        this.participants = participants;
    }


    public Player getHost() {
        return host;
    }

    public void setHost(Player host) {
        this.host = host;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public GameNightStatus getStatus() {
        return status;
    }

    public void setStatus(GameNightStatus status) {
        this.status = status;
    }

    public @NotNull Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(@NotNull Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist // set prePersist (createdAt) automatically at the first persistence
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }

    }

    public Set<Game> getGames() {
       return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    //helper method
    public void addGame(Game game) {
        games.add(game);
        game.getGameNights().add(this);
    }

    public void removeGame(Game game) {
        games.remove(game);
        game.getGameNights().remove(this);
    }
}
