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
		int itemQuantity = 0;

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
			System.out.println(
				i.name + 
				" | $" + i.price
			);
		}

		System.out.println();

		System.out.println("-------------------");

		while (!correct.equalsIgnoreCase("y")) {
			// get initial input from user
			System.out.println("Please enter your name: ");
			name = scanner.nextLine();

			System.out.println("Please enter your state of residence (for example MO): ");
			state = scanner.nextLine();

			System.out.println("Please enter the item to be purchased: ");
			itemName = scanner.nextLine();

			System.out.println("Please enter the quantity of items to be purchased: ");
			itemQuantity = Integer.parseInt(scanner.nextLine());

			System.out.println("Please enter either \\\"standard\\\" or \\\"next day\\\" shipping: ");
			shipping = scanner.nextLine();

			// display user input
			System.out.println();

			System.out.println("Name: " + name);
			System.out.println("State of Residence: " + state);
			System.out.println("Items to be purchased: " + itemName);
			System.out.println("Quantity of items: " + itemQuantity);
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
		System.out.println();

		while (!option.equalsIgnoreCase("7")) {
			// switch statement and handle exit
			switch(option) {
			case "1":
				// add item
				String addItem = "";
				Item foundItem = null;
				int addItemQuantity;

				System.out.println();
				System.out.println("Please enter the name of the item to be added: ");
				addItem = scanner.nextLine();

				System.out.println();
				System.out.println("Please enter the quantity of this item to be added: ");
				addItemQuantity = Integer.parseInt(scanner.nextLine());

				for (Item i : catalogue) {
					if (addItem.equals(i.name)) {
						foundItem = i;
					}
				}

				if (foundItem != null) {
					cart.addToCart(foundItem, addItemQuantity);
				}

				break;
			case "2":
				// get current total
				System.out.println();
				System.out.printf("Subtotal: $%.2f", cart.getSubtotal());
				System.out.println();
				System.out.printf("Tax: $%.2f", cart.getTax());
				System.out.println();
				System.out.printf("Shipping: $%.2f", cart.getShippingCost());
				System.out.println();
				System.out.printf("Current Total: $%.2f", cart.getTotal());
				System.out.println();

				break;
			case "3":
				// checkout
				cart.checkout();
				System.out.println();
				System.out.println("Transaction completed!");

				System.out.println();
				System.out.println("Goodbye! :p");
				System.out.println();

				return;
			case "4":
				// see contents
				cart.getContents();

				break;
			case "5":
				// edit quantity
				String editItem = "";
				int editQuantity;

				System.out.println();
				System.out.println("Please enter the item you would like to edit: ");
				editItem = scanner.nextLine();

				System.out.println();
				System.out.println("Please enter the new quantity: ");
				editQuantity = Integer.parseInt(scanner.nextLine());

				cart.editItemQuantity(editItem, editQuantity);

				break;
			case "6":
				// remove item
				String removeItem = "";

				System.out.println();
				System.out.println("Please enter the name of the item to be removed: ");
				removeItem = scanner.nextLine();

				cart.removeItem(removeItem);

				break;
			}
			System.out.println();
			System.out.println("Please select another option: ");
			option = scanner.nextLine();
			System.out.println();
		}

		// exit out of the program
		System.out.println();
		System.out.println("Goodbye! :p");
		System.out.println();

		scanner.close();
	}
}
