package com.caitlinash.shoppingapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemTests {
    // Make sure Items are initialized correctly

    @Test
    void constructorInitializesFieldsCorrectly() {
        Item item = new Item("Notebook", 12.99);

        assertEquals("Notebook", item.name);
        assertEquals(12.99, item.price);
    }
}