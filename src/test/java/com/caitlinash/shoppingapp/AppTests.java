package com.caitlinash.shoppingapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class AppTests {
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;

    // Restore normal console input and output after each test
    @AfterEach
    void restoreStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    void fullUserSession_getTotal_thenExit() {
        String input =
                "Caitlin\n" +
                        "IL\n" +
                        "Notebook\n" +
                        "2\n" +
                        "standard\n" +
                        "y\n" +
                        "2\n" +             // get current total
                        "7\n";              // exit

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        App.main(new String[]{});

        String result = output.toString();

        assertTrue(result.contains("Subtotal"));
        assertTrue(result.contains("Tax"));
        assertTrue(result.contains("Current Total"));
        assertTrue(result.contains("-------------------"));
        assertTrue(result.contains("--------------"));
        long newlineCount = result.chars().filter(ch -> ch == '\n').count();
        assertTrue(newlineCount > 25, "Should have many newlines for formatting");
    }

    @Test
    void case1_addItem() {
        String input =
                "Caitlin\n" +
                        "MO\n" +
                        "Notebook\n" +
                        "1\n" +
                        "standard\n" +
                        "y\n" +
                        "1\n" +             // add item
                        "Notebook\n" +      // try adding existing item from catalogue
                        "2\n" +
                        "7\n";              // exit

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        App.main(new String[]{});

        String result = output.toString();
        // Verify the item was actually found and added
        assertTrue(result.contains("2 item(s) added to cart"));
        assertTrue(result.contains("Current cart item quantity: 3"));
        assertTrue(result.contains("Command Options"));
        assertTrue(result.contains("-----------------"));
        assertTrue(result.contains("Please select"));
        assertTrue(result.contains("Please enter your name"));
        assertTrue(result.contains("Please enter your state"));
        // Verify blank lines exist
        long newlineCount = result.chars().filter(ch -> ch == '\n').count();
        assertTrue(newlineCount > 30, "Should have many newlines");
    }

    @Test
    void case3_checkout() {
        String input =
                "Caitlin\n" +
                        "MO\n" +
                        "Notebook\n" +
                        "1\n" +
                        "standard\n" +
                        "y\n" +
                        "3\n";              // checkout

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        App.main(new String[]{});

        String result = output.toString();
        assertTrue(result.contains("Subtotal:"));
        assertTrue(result.contains("Tax:"));
        assertTrue(result.contains("Shipping:"));
        assertTrue(result.contains("Final Total:"));
        assertTrue(result.contains("Transaction completed!"));
        assertTrue(result.contains("Goodbye! :p"));
        // Verify formatting with blank lines
        int lineCount = result.split("\n").length;
        assertTrue(lineCount > 25, "Should have blank lines throughout for formatting");
    }

    @Test
    void case4_seeContents() {
        String input =
                "Caitlin\n" +
                        "MO\n" +
                        "Notebook\n" +
                        "2\n" +
                        "standard\n" +
                        "y\n" +
                        "4\n" +             // see contents
                        "7\n";              // exit

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        App.main(new String[]{});

        String result = output.toString();
        assertTrue(result.contains("Your Cart Contents"));
        assertTrue(result.contains("------------------"));
        assertTrue(result.contains("Notebook"));
        assertTrue(result.contains("Command Options"));
    }

    @Test
    void case5_editQuantity() {
        String input =
                "Caitlin\n" +
                        "MO\n" +
                        "Notebook\n" +
                        "2\n" +
                        "standard\n" +
                        "y\n" +
                        "5\n" +                 // edit quantity
                        "Notebook\n" +
                        "5\n" +
                        "7\n";                  // exit

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        App.main(new String[]{});

        String result = output.toString();
        assertTrue(result.contains("Quantity updated"));
        assertTrue(result.contains("Command Options"));
        assertTrue(result.contains("-----------------"));
    }

    @Test
    void case6_removeItem() {
        String input =
                "Caitlin\n" +
                        "MO\n" +
                        "Notebook\n" +
                        "1\n" +
                        "standard\n" +
                        "y\n" +
                        "6\n" +                 // remove item
                        "Notebook\n" +
                        "7\n";                  // exit

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        App.main(new String[]{});

        String result = output.toString();
        assertTrue(result.contains("Item removed"));
        assertTrue(result.contains("Command Options"));
        assertTrue(result.contains("-----------------"));
    }

    @Test
    void displaysCatalogue() {
        String input =
                "Caitlin\n" +
                        "MO\n" +
                        "Notebook\n" +
                        "1\n" +
                        "standard\n" +
                        "y\n" +
                        "7\n";              // exit

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        App.main(new String[]{});

        String result = output.toString();
        assertTrue(result.contains("Welcome to ShopCLI!"));
        assertTrue(result.contains("-------------------"));
        assertTrue(result.contains("Shop Inventory"));
        assertTrue(result.contains("--------------"));
        assertTrue(result.contains("Notebook"));
        assertTrue(result.contains("Journal"));
        assertTrue(result.contains("Planner"));
        // Verify blank lines exist for formatting
        assertTrue(result.split("\n").length > 30, "Should have many lines including blanks");
    }

    @Test
    void exitWithOption7() {
        String input =
                "Caitlin\n" +
                        "MO\n" +
                        "Notebook\n" +
                        "1\n" +
                        "standard\n" +
                        "y\n" +
                        "7\n";              // exit

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        App.main(new String[]{});

        String result = output.toString();
        assertTrue(result.contains("Goodbye! :p"));
    }
}