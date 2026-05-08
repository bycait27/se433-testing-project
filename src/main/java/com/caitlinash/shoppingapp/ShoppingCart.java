package com.caitlinash.shoppingapp;

import java.util.List;

/*
 * ShoppingCart Class provides methods for interacting with the cart
 */

class ShoppingCart {
	User user;
	List<CartItem> items;

	ShoppingCart(User user, List<CartItem> items) {
		this.user = user;
		this.items = items;
	}

	// method add items
	public void addToCart(Item item, int quantity) {
		CartItem cartItem = new CartItem(item, quantity);
		if (cartItem.quantity >= 1) {
			items.add(cartItem);

			System.out.println();
			System.out.println(quantity + " item(s) added to cart");
			System.out.println();
			System.out.println("Current cart item quantity: " + getItemCount());
			return;
		}
		System.out.println();
		System.out.println("Quantity cannot be less than 1");
	}

	// method see contents
	public void getContents() {
		System.out.println();
        System.out.println("Your Cart Contents");
        System.out.println("------------------");

        for (CartItem i : items) {
            System.out.println(
				i.item.name + 
				" | Quantity: " + i.quantity + 
				" | Price: $" + i.item.price
			);
        }
	}

	// method edit quantity of an item
	public void editItemQuantity(String itemName, int newQuantity) {
		if (newQuantity < 1) {
			System.out.println();
			System.out.println("Quantity cannot be less than 1");
			return;
		}
		for (CartItem i : items) {
			if (i.item.name.equals(itemName)) {
				i.quantity = newQuantity;
				System.out.println();
				System.out.println("Quantity updated");
				return;
			}
		}
		System.out.println();
		System.out.println("Item not found");
	}

	// method remove items 
	public void removeItem(String itemName) {
		for (CartItem i : items) {
			if (i.item.name.equals(itemName)) {
				items.remove(i);
				System.out.println();
				System.out.println("Item removed");
				return;
			}
		}
		System.out.println();
		System.out.println("Item not found");
	}

	// get total item quantity
	public int getItemCount() {
		int count = 0;

		for (CartItem i : items) {
			count += i.quantity;
		}
		return count;
	}

	// helper methods for checkout

	public double getTotal() { 
		return getSubtotal() + getTax() + getShippingCost();
	}

	public double getSubtotal() {
		double subtotal = 0;

		for (CartItem i : items) {
			subtotal += i.item.price * i.quantity;
		}
		return subtotal;
	}

	public double getTax() {
		double subtotal = getSubtotal();

		if (
			user.state.equals("IL") ||
			user.state.equals("CA") ||
			user.state.equals("NY")
		) {
			return subtotal * 0.06;
		}
		return 0;
	}

	public double getShippingCost() {
		double subtotal = getSubtotal();

		// standard shipping
		if (user.shipping.equals("standard")) {
			if (subtotal > 50) {
				return 0;
			}
			return 10;
		}

		// next day shipping
		if (user.shipping.equals("next day")) {
			return 25;
		}
		return 0;
	}

	public double checkout() {
		double subtotal = getSubtotal();
		double tax = getTax();
		double shipping = getShippingCost();
		double total = subtotal + tax + shipping;

		if (total >= 1 && total < 99999.99) {
			System.out.printf("Subtotal: $%.2f", subtotal);
			System.out.println();
			System.out.printf("Tax: $%.2f", tax);
			System.out.println();
			System.out.printf("Shipping: $%.2f", shipping);
			System.out.println();
			System.out.printf("Final Total: $%.2f", total);
			System.out.println();
			return total;
		}
		System.out.println();
		System.out.println("You may only purchase an amount between $1 and $99,999.99");

		return 0;
	}
}