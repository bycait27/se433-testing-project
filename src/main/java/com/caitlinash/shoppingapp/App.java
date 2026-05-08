package com.caitlinash.shoppingapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * ShopCLI App
 * App interface: handles logic of shopping app, interacts with user
 */

public class App {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		String correct = "n";

		String name = "";
		String state = "";
		String shipping = "";

		String itemName = "";
		String quantity = "";

		List<CartItem> cartItems = new ArrayList<>();

		List<Item> catalogue = new ArrayList<>(List.of(
			new Item("Notebook", 12.99), 
			new Item("Journal", 10.50),
			new Item("Planner", 15.99),
			new Item("Mini Pocket Notebook Set", 9.99),
			new Item("Grid Study Notebook", 8.75),
			new Item("Gel Pen Set", 14.99),
			new Item("Highlighter Set", 11.99),
			new Item("Fine Line Pen Pack (Black)", 9.50),
			new Item("Glitter Pen Set", 13.50),
			new Item("Mechanical Pencil", 6.99),
			new Item("Animal Sticker Pack", 5.99),
			new Item("Stars Sticker Sheet", 3.50),
			new Item("Planner Stickers", 4.25),
			new Item("Seasonal Sticker Bundle", 7.50),
			new Item("Washi Tape Set (3 rolls)", 8.99),
			new Item("Sticky Notes", 4.50),
			new Item("Bookmark Set (Magnetic, 4-pack)", 6.75),
			new Item("Pencil Pouch", 12.00),
			new Item("Desk Organizer Tray", 14.50),
			new Item("Eraser Set", 3.99)
		));

		System.out.println();
		System.out.println("Welcome to ShopCLI!");

		System.out.println("-------------------");

		System.out.println("Shop Inventory");

		System.out.println("--------------");

		for (Item i : catalogue) {
			System.out.println("Item Name: " + i.name + " Item Price: " + i.price);
		}

		System.out.println("-------------------");

		while (!correct.equalsIgnoreCase("y")) {
			// get initial input from user
			System.out.println("Please enter your name: ");
			name = scanner.nextLine();

			System.out.println("Please enter your state of residence: ");
			state = scanner.nextLine();

			System.out.println("Please enter the item to be purchased: ");
			itemName = scanner.nextLine();

			System.out.println("Please enter the quantity of items to be purchased: ");
			quantity = scanner.nextLine();

			System.out.println("Please enter either \\\"standard\\\" or \\\"next day\\\" shipping: ");
			shipping = scanner.nextLine();

			// display user input
			System.out.println();

			System.out.println("Name: " + name);
			System.out.println("State of Residence: " + state);
			System.out.println("Items to be purchased: " + itemName);
			System.out.println("Quantity of items: " + quantity);
			System.out.println("Shipping: " + shipping + " shipping");

			System.out.println();

			// make sure user input is correct before proceeding
			System.out.println("Please enter \\\"y\\\" if the information is correct, otherwise, enter \\\"n\\\": ");
			correct = scanner.nextLine();
		}

		// create User with above information
		User user = new User(name, state, shipping);

		// create ShoppingCart with user items
		ShoppingCart cart = new ShoppingCart(user, cartItems);

		Item cartItem = null;

		for (Item i : catalogue) {
			if (itemName.equals(i.name)) {
			cartItem = i;
			}
		}
		
		int itemQuantity = Integer.parseInt(quantity);
		cart.addToCart(cartItem, itemQuantity);

		// let user interact with cart/items
		System.out.println();
		System.out.println("Command Options: ");
		System.out.println("-----------------");
		System.out.println("1. Add item to the shopping cart");
		System.out.println("2. Get current total");
		System.out.println("3. Checkout");
		System.out.println("4. See contents of the shopping cart");
		System.out.println("5. Edit quantity of items in the shopping cart");
		System.out.println("6. Remove items from the shopping cart");
		System.out.println("7. Exit");
		System.out.println();

		System.out.println("Please select what to do next from the options above (enter number): ");
		String option = scanner.nextLine();

		while (!option.equalsIgnoreCase("7")) {
			// switch statement and handle exit
			switch(option) {
			case "1":
				// add item
				String addItem = "";
				Item foundItem = null;
				String addItemQuantity = "";

				System.out.println("Please enter the name of the item to be added: ");
				addItem = scanner.nextLine();

				System.out.println("Please enter the quantity of this item to be added: ");
				addItemQuantity = scanner.nextLine();

				for (Item i : catalogue) {
					if (addItem.equals(i.name)) {
						foundItem = i;
					}
				}

				int newItemQuantity = Integer.parseInt(addItemQuantity);

				if (foundItem != null) {
					cart.addToCart(foundItem, newItemQuantity);
				}

				break;
			case "2":
				// get current total
				System.out.println("Subtotal: $" + cart.getSubtotal());

				System.out.println("Tax: $" + cart.getTax());

				System.out.println(
					"Shipping: $" + cart.getShippingCost()
				);

				System.out.println(
					"Current Total: $" + cart.getTotal()
				);

				break;
			case "3":
				// checkout
				cart.checkout();
				System.out.println("Transaction completed!");

				break;
			case "4":
				// see contents
				cart.getContents();

				break;
			case "5":
				// edit quantity

				break;
			case "6":
				// remove item
				String removeItem = "";

				System.out.println("Please enter the name of the item to be removed: ");
				removeItem = scanner.nextLine();

				cart.removeItem(removeItem);

				break;
			}
			System.out.println();
			System.out.println("Please select another option: ");
			option = scanner.nextLine();
		}

		// exit out of the program
		System.out.println();
		System.out.println("Goodbye! :p");

		scanner.close();
	}
}
