package com.caitlinash.shoppingapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTests {
    // Make sure Users are initialized correctly

    @Test
    void constructorInitializesFieldsCorrectly() {
        User user = new User("Caitlin", "MO", "standard");

        assertEquals("Caitlin", user.name);
        assertEquals("MO", user.state);
        assertEquals("standard", user.shipping);
    }
}