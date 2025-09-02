package com.iu.gameboardapp.model;

import jakarta.persistence.*;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne
    private Player player;

    @ManyToOne
    private GameNight gameNight;

    private int hostRating;   // 1–5
    private int foodRating;   // 1–5
    private int funRating;    // 1–5

    public Review(Long id, Player player, GameNight gameNight, int hostRating, int foodRating, int funRating) {
        this.id = id;
        this.player = player;
        this.gameNight = gameNight;
        this.hostRating = hostRating;
        this.foodRating = foodRating;
        this.funRating = funRating;

    }

    public Review() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getHostRating() {
        return hostRating;
    }

    public void setHostRating(int hostRating) {
        this.hostRating = hostRating;
    }

    public int getFoodRating() {
        return foodRating;
    }

    public void setFoodRating(int foodRating) {
        this.foodRating = foodRating;
    }

    public int getFunRating() {
        return funRating;
    }

    public void setFunRating(int funRating) {
        this.funRating = funRating;
    }
}
