package com.caitlinash.shoppingapp;

/*
* CartItem Class defines the item info and quantity of the item a user has in their cart
*/

public class CartItem {
	Item item;
	Integer quanitity;

	CartItem(Item item, Integer quantity) {
		this.item = item;
		this.quanitity = quantity;
	}
	
}