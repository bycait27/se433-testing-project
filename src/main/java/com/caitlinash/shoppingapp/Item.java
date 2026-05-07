package com.caitlinash.shoppingapp;

/*
* Item Class defines the name and price of the item a user chooses from the catalogue
*/

public class Item {
	String name;
	Double price;

	Item(String name, double price) {
        this.name = name;
        this.price = price;
    }
}