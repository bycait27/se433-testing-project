package com.caitlinash.shoppingapp;

/*
* User Class defines the name, state of residence, and shipping selection of the user
*/

public class User {
	String name;
	String state;
	String shipping;

	public User(String name, String state, String shipping) {
		this.name = name;
		this.state = state;
		this.shipping = shipping;
	}
}