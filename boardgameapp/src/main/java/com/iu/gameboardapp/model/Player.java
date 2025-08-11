package com.iu.gameboardapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import com.iu.gameboardapp.model.enums.FoodType;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotNull
    private FoodType foodPreference;
    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean active;
    @NotBlank
    private String phoneNumber;

    @ManyToMany
    @JoinTable(
            name = "player_game_night",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "game_night_id")
    )

    //@JsonBackReference(value = "player-gameNight")
    private List<GameNight> gameNights = new ArrayList<>();

    @OneToMany(mappedBy = "host")
   // @JsonBackReference(value = "player-hostedNights")
    private List<GameNight> hostedNights = new ArrayList<>();


    // player can cast multiple votes
    @OneToMany(mappedBy = "player" )
    private List<Vote> votes;

    //helper method
    public void addVote(Vote vote) {
        votes.add(vote);
        vote.setPlayer((this));
    }


    // a player can propose many games
    @OneToMany(mappedBy = "proposedBy", cascade = CascadeType.ALL) // cascadeType.All ensures that the game also saved when the player saves
    private List<Game> proposedGame;

    public void addGame(Game game) {
        proposedGame.add(game);
        game.setProposedBy(this);
    }

    @OneToMany(mappedBy = "receiver")
    private List<Message> playerMessage;


    public Player(String name, String email, String password,
                  FoodType foodPreference, boolean isActive, String phoneNumber, List<Game> proposedGame) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.foodPreference = foodPreference;
        this.active = isActive;
        this.phoneNumber = phoneNumber;
        this.proposedGame = proposedGame;
    }

    public Player() {

    }


    public Long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public List<GameNight> getGameNights() {
        return gameNights;
    }

    public void setGameNights(List<GameNight> gameNights) {
        this.gameNights = gameNights;
    }

    public List<GameNight> getHostedNights() {
        return hostedNights;
    }

    public void setHostedNights(List<GameNight> hostedNights) {
        this.hostedNights = hostedNights;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank String email) {
        this.email = email;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public @NotNull FoodType getFoodPreference() {
        return foodPreference;
    }

    public void setFoodPreference(@NotNull FoodType foodPreference) {
        this.foodPreference = foodPreference;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public @NotBlank String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NotBlank String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



}
