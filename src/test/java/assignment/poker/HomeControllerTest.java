package assignment.poker;

import assignment.poker.controllers.HomeController;

import org.junit.Test;
import static org.junit.Assert.*;

public class HomeControllerTest {
    @Test
    public void homeControllerHasGreetings() {
        HomeController controllerUnderTest = new HomeController();
        assertNotNull(
            "Welcome to Chinese Poker Assignment.",
            controllerUnderTest.getGreeting()
        );
    }
}
