package model;

import com.iu.gameboardapp.model.Player;
import com.iu.gameboardapp.model.enums.FoodType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerTest {

    @Test
    void testNoArgsConstructor() {
        Player player = new Player();
        assertThat(player).isNotNull();
    }

    @Test
    void testAllArgsConstructorAndGetters() {
        Player player = new Player(
                1L,
                "John Doe",
                "john@example.com",
                "password123",
                FoodType.CHINESE,
                true,
                "+49123456789"
        );

        assertThat(player.getId()).isEqualTo(1L);
        assertThat(player.getName()).isEqualTo("John Doe");
        assertThat(player.getEmail()).isEqualTo("john@example.com");
        assertThat(player.getPassword()).isEqualTo("password123");
        assertThat(player.getFoodPreference()).isEqualTo(FoodType.CHINESE);
        assertThat(player.isActive()).isTrue();
        assertThat(player.getPhoneNumber()).isEqualTo("+49123456789");
    }

    @Test
    void testSetters() {
        Player player = new Player();

        player.setId(2L);
        player.setName("Jane Doe");
        player.setEmail("jane@example.com");
        player.setPassword("secret456");
        player.setFoodPreference(FoodType.GREEK);
        player.setActive(false);
        player.setPhoneNumber("+491987654321");

        assertThat(player.getId()).isEqualTo(2L);
        assertThat(player.getName()).isEqualTo("Jane Doe");
        assertThat(player.getEmail()).isEqualTo("jane@example.com");
        assertThat(player.getPassword()).isEqualTo("secret456");
        assertThat(player.getFoodPreference()).isEqualTo(FoodType.GREEK);
        assertThat(player.isActive()).isFalse();
        assertThat(player.getPhoneNumber()).isEqualTo("+491987654321");
    }
}