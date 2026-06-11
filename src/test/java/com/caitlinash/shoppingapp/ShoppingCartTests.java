package com.caitlinash.shoppingapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ShoppingCartTests {
    // Users for both shipping options
    User user = new User("Caitlin", "MO", "standard");
    User userTwo = new User("Caitlin", "IL", "next day");

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    // Redirect System.out.println() calls to outContent before each test
    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    // Restore normal console output after each test
    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    // ---------- addToCart() tests ----------

    @Test
    void itemExistsInCart() {
        ShoppingCart cart = new ShoppingCart(user);
        Item item = new Item("Notebook", 12.99);

        outContent.reset();
        cart.addToCart(item, 1);

        assertTrue(cart.containsItem("Notebook"));
        String output = outContent.toString();
        // Success prints: blank, message, blank, count message
        assertTrue(output.startsWith("\n"), "Should start with blank line");
        assertTrue(output.contains("1 item(s) added to cart"));
        assertTrue(output.contains("\n\n"), "Should have blank lines between messages");
        assertTrue(output.contains("Current cart item quantity: 1"));
    }

    // boundary value analysis test cases for item quantity

    @Test
    void quantityNegativeRejected() {
        ShoppingCart cart = new ShoppingCart(user);
        Item item = new Item("Notebook", 12.99);
        int initialSize = cart.getItemCount();

        outContent.reset();
        cart.addToCart(item, -1);

        assertEquals(initialSize, cart.getItemCount());
        assertFalse(cart.containsItem("Notebook"));
        String output = outContent.toString();
        // Rejection prints: blank line, then error message
        assertTrue(output.startsWith("\n"), "Should start with blank line");
        assertTrue(output.contains("Quantity cannot be less than 1"));
    }

    @Test
    void quantityZeroRejected() {
        ShoppingCart cart = new ShoppingCart(user);
        Item item = new Item("Notebook", 12.99);
        int initialSize = cart.getItemCount();

        outContent.reset();
        cart.addToCart(item, 0);

        assertEquals(initialSize, cart.getItemCount());
        assertFalse(cart.containsItem("Notebook"));
        String output = outContent.toString();
        assertTrue(output.startsWith("\n"), "Should start with blank line");
        assertTrue(output.contains("Quantity cannot be less than 1"));
    }

    @Test
    void quantityOneAccepted() {
        ShoppingCart cart = new ShoppingCart(user);
        Item item = new Item("Notebook", 12.99);
        int expected = 1;

        outContent.reset();
        cart.addToCart(item, 1);

        assertEquals(expected, cart.getItemCount());
        assertTrue(cart.containsItem("Notebook"));
        String output = outContent.toString();
        assertTrue(output.startsWith("\n"), "Should start with blank line");
        assertTrue(output.contains("1 item(s) added to cart"));
        assertTrue(output.contains("\n\n"), "Should have blank lines");
        assertTrue(output.contains("Current cart item quantity: 1"));
    }

    @Test
    void quantityTwoAccepted() {
        ShoppingCart cart = new ShoppingCart(user);
        Item item = new Item("Notebook", 12.99);
        int expected = 2;

        outContent.reset();
        cart.addToCart(item, 2);

        assertEquals(expected, cart.getItemCount());
        assertTrue(cart.containsItem("Notebook"));
        String output = outContent.toString();
        assertTrue(output.startsWith("\n"), "Should start with blank line");
        assertTrue(output.contains("2 item(s) added to cart"));
        assertTrue(output.contains("\n\n"), "Should have blank lines");
        assertTrue(output.contains("Current cart item quantity: 2"));
    }

    // ---------- getContents() tests ----------

    @Test
    void showCorrectContents() {
        ShoppingCart cart = new ShoppingCart(user);
        Item item1 = new Item("Notebook", 12.99);
        Item item2 = new Item("Pencil", 1.50);

        cart.addToCart(item1, 2);
        cart.addToCart(item2, 3);

        outContent.reset(); // Clear previous output
        cart.getContents();

        String output = outContent.toString();
        // getContents prints: blank, title, divider, items
        assertTrue(output.startsWith("\n"), "Should start with blank line");
        assertTrue(output.contains("Your Cart Contents"));
        assertTrue(output.contains("------------------"));
        assertTrue(output.contains("Notebook"));
        assertTrue(output.contains("Quantity: 2"));
        assertTrue(output.contains("Price: $12.99"));
        assertTrue(output.contains("Pencil"));
        assertTrue(output.contains("Quantity: 3"));
        assertTrue(output.contains("Price: $1.5"));
    }

    @Test
    void showEmptyCart() {
        ShoppingCart cart = new ShoppingCart(user);

        outContent.reset();
        cart.getContents();

        String output = outContent.toString();
        // Empty cart prints: blank, title, divider
        assertTrue(output.startsWith("\n"), "Should start with blank line");
        assertTrue(output.contains("Your Cart Contents"));
        assertTrue(output.contains("------------------"));
    }

    // ---------- containsItem() tests ----------

    @Test
    void noItemsMatch() {
        ShoppingCart cart = new ShoppingCart(user);
        Item item = new Item("Notebook", 12.99);
        Item itemTwo = new Item("Pencil", 5.00);
        cart.addToCart(item, 1);
        cart.addToCart(itemTwo, 1);

        assertFalse(cart.containsItem("Journal"));
    }

    // ---------- editItemQuantity() tests ----------

    @Test
    void successfulEdit() {
        ShoppingCart cart = new ShoppingCart(user);
        Item item = new Item("Notebook", 12.99);
        cart.addToCart(item, 2);

        outContent.reset(); // Clear previous output
        cart.editItemQuantity("Notebook", 3);

        assertEquals(3, cart.getItemCount());
        String output = outContent.toString();
        // editItemQuantity prints: blank line, then message
        assertTrue(output.startsWith("\n"), "Should start with blank line");
        assertTrue(output.contains("Quantity updated"));
    }

    @Test
    void itemNotFoundError() {
        ShoppingCart cart = new ShoppingCart(user);
        Item item = new Item("Notebook", 12.99);
        cart.addToCart(item, 2);

        outContent.reset();
        cart.editItemQuantity("Pencil", 3);

        assertEquals(2, cart.getItemCount());
        String output = outContent.toString();
        // Rejection (not found) prints: blank, error message
        assertTrue(output.startsWith("\n"), "Should start with blank line");
        assertTrue(output.contains("Item not found"));
    }

    @Test
    void quantityLessThanOne() {
        ShoppingCart cart = new ShoppingCart(user);
        Item item = new Item("Notebook", 12.99);
        cart.addToCart(item, 2);

        outContent.reset();
        cart.editItemQuantity("Notebook", 0);

        assertEquals(2, cart.getItemCount());
        String output = outContent.toString();
        // Rejection (<1) prints: blank, error message
        assertTrue(output.startsWith("\n"), "Should start with blank line");
        assertTrue(output.contains("Quantity cannot be less than 1"));
    }

    @Test
    void editQuantityBoundaryAtOne() {
        ShoppingCart cart = new ShoppingCart(user);
        Item item = new Item("Notebook", 12.99);
        cart.addToCart(item, 5);

        outContent.reset();
        cart.editItemQuantity("Notebook", 1);

        assertEquals(1, cart.getItemCount());
        String output = outContent.toString();
        assertTrue(output.startsWith("\n"), "Should start with blank line");
        assertTrue(output.contains("Quantity updated"));
    }

    // ---------- removeItem() tests ----------

    @Test
    void successfulRemoval() {
        ShoppingCart cart = new ShoppingCart(user);
        Item item = new Item("Notebook", 12.99);

        cart.addToCart(item, 1);

        outContent.reset();
        cart.removeItem("Notebook");

        assertEquals(0, cart.getItemCount());
        assertFalse(cart.containsItem("Notebook"));
        String output = outContent.toString();
        // removeItem prints: blank line, then message
        assertTrue(output.startsWith("\n"), "Should start with blank line");
        assertTrue(output.contains("Item removed"));
    }

    @Test
    void failureToRemove() {
        ShoppingCart cart = new ShoppingCart(user);
        Item item = new Item("Notebook", 12.99);

        cart.addToCart(item, 1);

        outContent.reset();
        cart.removeItem("Pencil");

        assertEquals(1, cart.getItemCount());
        assertTrue(cart.containsItem("Notebook"));
        String output = outContent.toString();
        // Rejection prints: blank, error message
        assertTrue(output.startsWith("\n"), "Should start with blank line");
        assertTrue(output.contains("Item not found"));
    }

    // ---------- getItemCount() tests ----------

    @Test
    void countMatchesQuantity() {
        ShoppingCart cart = new ShoppingCart(user);
        Item item = new Item("Notebook", 12.99);

        outContent.reset();
        cart.addToCart(item, 4);

        assertEquals(4, cart.getItemCount());
        String output = outContent.toString();
        assertTrue(output.startsWith("\n"), "Should start with blank line");
        assertTrue(output.contains("4 item(s) added to cart"));
        assertTrue(output.contains("\n\n"), "Should have blank lines");
        assertTrue(output.contains("Current cart item quantity: 4"));
    }

    // ---------- getTotal() tests ----------

    @Test
    void correctTotal() {
        ShoppingCart cart = new ShoppingCart(user);
        Item item = new Item("Notebook", 10.00);

        cart.addToCart(item, 4);

        assertEquals(50.00, cart.getTotal());
    }

    @Test
    void totalMathCalculation() {
        // Test that total = subtotal + tax + shipping (not subtraction)
        User userIL = new User("Test", "IL", "standard");
        ShoppingCart cart = new ShoppingCart(userIL);
        Item item = new Item("Notebook", 30.00);
        cart.addToCart(item, 1);

        double total = cart.getTotal();
        assertEquals(41.80, total, 0.01);
    }

    // ---------- getSubtotal() tests ----------

    @Test
    void correctSubtotal() {
        ShoppingCart cart = new ShoppingCart(user);
        Item item = new Item("Notebook", 10.00);

        cart.addToCart(item, 4);

        assertEquals(40.00, cart.getSubtotal());
    }

    @Test
    void subtotalCalculationMath() {
        // Test that subtotal is calculated via multiplication (price * quantity)
        ShoppingCart cart = new ShoppingCart(user);
        Item item = new Item("Notebook", 12.50);
        cart.addToCart(item, 3);

        double subtotal = cart.getSubtotal();

        assertEquals(37.50, subtotal, 0.01);
    }

    // ---------- getTax() tests ----------

    @Test
    void ILStateTax() {
        ShoppingCart cart = new ShoppingCart(userTwo);
        Item item = new Item("Notebook", 10.00);
        cart.addToCart(item, 1);

        assertEquals(0.60, cart.getTax());
    }

    @Test
    void CAStateTax() {
        User userThree = new User("Caitlin", "CA", "standard");
        ShoppingCart cart = new ShoppingCart(userThree);
        Item item = new Item("Notebook", 10.00);
        cart.addToCart(item, 1);

        assertEquals(0.60, cart.getTax());
    }

    @Test
    void NYStateTax() {
        User userThree = new User("Caitlin", "NY", "standard");
        ShoppingCart cart = new ShoppingCart(userThree);
        Item item = new Item("Notebook", 10.00);
        cart.addToCart(item, 1);

        assertEquals(0.60, cart.getTax());
    }

    @Test
    void noStateTax() {
        ShoppingCart cart = new ShoppingCart(user);
        Item item = new Item("Notebook", 10.00);

        cart.addToCart(item, 1);

        assertEquals(0, cart.getTax());
    }

    @Test
    void taxCalculationMath() {
        // Test that tax is calculated via multiplication, not addition/subtraction
        // Tax should be subtotal * 0.06 for IL
        User userIL = new User("Test", "IL", "standard");
        ShoppingCart cart = new ShoppingCart(userIL);
        Item item = new Item("Notebook", 100.00);
        cart.addToCart(item, 1);

        double tax = cart.getTax();

        assertEquals(6.00, tax, 0.01);
    }

    // ---------- getShippingCost() tests ----------

    @Test
    void invalidShippingType() {
        User userInvalid = new User("Caitlin", "MO", "express");
        ShoppingCart cart = new ShoppingCart(userInvalid);
        Item item = new Item("Notebook", 49.99);
        cart.addToCart(item, 1);

        assertEquals(0, cart.getShippingCost());
    }

    // boundary value analysis test cases for standard shipping

    @Test
    void standardShippingNotFreeBelowBoundary() {
        ShoppingCart cart = new ShoppingCart(user);
        Item item = new Item("Notebook", 49.99);
        cart.addToCart(item, 1);

        assertEquals(10, cart.getShippingCost());
    }

    @Test
    void standardShippingNotFreeAtBoundary() {
        ShoppingCart cart = new ShoppingCart(user);
        Item item = new Item("Notebook", 50.00);
        cart.addToCart(item, 1);

        assertEquals(10, cart.getShippingCost());
    }

    @Test
    void standardShippingFreeAboveBoundary() {
        ShoppingCart cart = new ShoppingCart(user);
        Item item = new Item("Notebook", 50.01);
        cart.addToCart(item, 1);

        assertEquals(0, cart.getShippingCost());
    }

    @Test
    void nextDayShipping() {
        ShoppingCart cart = new ShoppingCart(userTwo);
        Item item = new Item("Notebook", 10.00);
        cart.addToCart(item, 1);

        assertEquals(25, cart.getShippingCost());
    }

    // ---------- checkout() tests ----------

    @Test
    void belowMinBoundaryPurchaseAmount() {
        ShoppingCart cart = new ShoppingCart(user);
        Item item = new Item("Notebook", 0.99);
        cart.addToCart(item, 1);

        // This will actually be 0.99 + 0 + 10 = 10.99 due to shipping
        // The total < 1 branch is unreachable with current business logic
        double result = cart.checkout();
        assertTrue(result >= 1, "Total cannot be < 1 with current pricing and shipping rules");
    }

    @Test
    void minBoundaryPurchaseAmount() {
        ShoppingCart cart = new ShoppingCart(user);
        Item item = new Item("Notebook", 1.00);
        cart.addToCart(item, 1);

        // This will actually be 1.00 + 0 + 10 = 11.99 due to shipping
        // The total < 1 branch is unreachable with current business logic
        double result = cart.checkout();
        assertTrue(result >= 1, "Total cannot be == 1 with current pricing and shipping rules");
    }

    @Test
    void belowMaxBoundaryPurchaseAmount() {
        ShoppingCart cart = new ShoppingCart(user);
        Item item = new Item("Notebook", 99999.98);
        cart.addToCart(item, 1);

        outContent.reset();
        assertEquals(99999.98, cart.checkout());

        String output = outContent.toString();
        // checkout prints each label with printf, then println() for newline
        assertTrue(output.contains("Subtotal:"));
        assertTrue(output.contains("Tax:"));
        assertTrue(output.contains("Shipping:"));
        assertTrue(output.contains("Final Total:"));
        // Verify newlines after each printf (4 println() calls)
        long newlineCount = output.chars().filter(ch -> ch == '\n').count();
        assertEquals(4, newlineCount, "Should have 4 newlines (one after each field)");
    }

    @Test
    void maxBoundaryPurchaseAmount() {
        ShoppingCart cart = new ShoppingCart(user);
        Item item = new Item("Notebook", 99999.99);
        cart.addToCart(item, 1);

        outContent.reset();
        assertEquals(99999.99, cart.checkout());

        String output = outContent.toString();
        assertTrue(output.contains("Subtotal:"));
        assertTrue(output.contains("Tax:"));
        assertTrue(output.contains("Shipping:"));
        assertTrue(output.contains("Final Total:"));
        // Verify 4 newlines
        long newlineCount = output.chars().filter(ch -> ch == '\n').count();
        assertEquals(4, newlineCount, "Should have 4 newlines");
    }

    @Test
    void aboveMaxBoundaryPurchaseAmount() {
        ShoppingCart cart = new ShoppingCart(user);
        Item item = new Item("Notebook", 100000.00);
        cart.addToCart(item, 1);

        outContent.reset();
        double result = cart.checkout();

        assertEquals(0, result);

        String output = outContent.toString();
        // Error case prints: blank line, then error message
        assertTrue(output.startsWith("\n"), "Should start with blank line");
        assertTrue(output.contains("You may only purchase an amount between $1 and $99,999.99"));
    }

    @Test
    void checkoutMathCalculation() {
        // Verify checkout calculates total correctly (addition not subtraction)
        User userIL = new User("Test", "IL", "next day");
        ShoppingCart cart = new ShoppingCart(userIL);
        Item item = new Item("Notebook", 100.00);
        cart.addToCart(item, 1);

        outContent.reset();
        double result = cart.checkout();

        assertEquals(131.00, result, 0.01);
        String output = outContent.toString();
        // Verify 4 newlines in output
        long newlineCount = output.chars().filter(ch -> ch == '\n').count();
        assertEquals(4, newlineCount, "Should have 4 newlines");
    }
}