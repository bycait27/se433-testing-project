package com.caitlinash.shoppingapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartItemTests {
    // Make sure CartItems are initialized correctly

    @Test
    void constructorInitializesFieldsCorrectly() {
        Item item = new Item("Notebook", 12.99);
        CartItem cartItem = new CartItem(item, 1);

        assertEquals("Notebook", cartItem.item.name);
        assertEquals(12.99, cartItem.item.price);
        assertEquals(1, cartItem.quantity);
    }
}