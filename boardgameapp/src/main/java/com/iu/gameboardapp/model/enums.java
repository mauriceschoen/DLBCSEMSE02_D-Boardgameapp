package com.iu.gameboardapp.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class enums {


    public enum FoodType {
        ITALIAN,
        GREEK,
        TURKISH,
        CHINESE,
        PIZZA,
        INDIAN,
        THAI
    }

    public enum GameNightStatus {
        PLANNED,
        CONFIRMED,
        ONGOING,
        COMPLETED,
        CANCELLED
    }

    public enum VoteType {
        YES,
        NO,
        MAYBE
    }

    public enum MessageType {
        GENERAL,
        RUNNING_LATE,
        CANCELLATION,
        REMINDER
    }

    public enum InvitationResponse {
        ACCEPTED,
        DECLINED,
        PENDING
    }

    public enum OrderStatus {
        PENDING,
        CONFIRMED,
        DELIVERED,
        CANCELLED
    }

    public enum DifficultyLevel {
        EASY,
        MEDIUM,
        HARD,
        EXPERT
    }



}

