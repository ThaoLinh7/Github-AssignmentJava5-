package edu.poly.shop.service;


import edu.poly.shop.domain.Cart;
import edu.poly.shop.domain.CartItem;

import java.util.List;



public interface CartService {

	void removeItem(Long productId);

	void addItem(CartItem item);

	String toString();

	void setItems(List<CartItem> items);

	int hashCode();

	List<CartItem> getItems();

	boolean equals(Object o);

	double getTotal();

	void removeCart(Long cartItemId);

	void addProduct(Long productId, int quantity);

	Cart getCart();

	Cart cart = new Cart();

   
}
