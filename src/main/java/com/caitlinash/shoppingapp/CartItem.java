package com.caitlinash.shoppingapp;

/*
* CartItem Class defines the item info and quantity of the item a user has in their cart
*/

public class CartItem {
	Item item;
	Integer quantity;

	public CartItem(Item item, Integer quantity) {
		this.item = item;
		this.quantity = quantity;
	}
	
}