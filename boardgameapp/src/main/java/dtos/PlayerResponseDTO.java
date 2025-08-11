package dtos;


/*
A basic Java is not necessary we can use a record, because a dto
acts as a data storage and a record delivers Getter, setter ect. automatically


//import com.iu.gameboardapp.model.enums.FoodType;
//
//public class PlayerResponseDTO {
//
//    private Long id;
//    private String name;
//    private String email;
//    private String phoneNumber;
//    private FoodType foodPreference;
//
//    //
//    public PlayerResponseDTO(Long id, String name, String email, String phoneNumber, FoodType foodPreference) {
//        this.id = id;
//        this.name = name;
//        this.email = email;
//        this.phoneNumber = phoneNumber;
//        this.foodPreference = foodPreference;
//    }
//
//    // empty constructor (important for Jackson)
//    public PlayerResponseDTO() {
//    }
//
//    // Getter & Setter
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public FoodType getFoodPreference() {
//        return foodPreference;
//    }
//
//    public void setFoodPreference(FoodType foodPreference) {
//        this.foodPreference = foodPreference;
//    }

}
 */

import com.iu.gameboardapp.model.enums.FoodType;

public record PlayerResponseDTO(
  Long id,
  String name,
  String email,
  String phoneNumber,
  FoodType foodPreference
) {}

