package model;
import com.iu.gameboardapp.model.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void testSetName_Valid() {
        Game game = new Game();
        game.setName("Monopoly");
        assertEquals("Monopoly", game.getName());
    }

    @Test
    void testSetName_Invalid_ThrowsException() {
        Game game = new Game();
        assertThrows(IllegalArgumentException.class, () -> game.setName(""));
    }
}