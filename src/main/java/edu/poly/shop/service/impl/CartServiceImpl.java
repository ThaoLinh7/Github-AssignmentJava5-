package edu.poly.shop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.shop.domain.Cart;
import edu.poly.shop.domain.CartItem;
import edu.poly.shop.domain.Product;
import edu.poly.shop.repository.ProductRepository;
import edu.poly.shop.service.CartService;
@Service
public class CartServiceImpl implements CartService{
	 @Autowired
	    private ProductRepository productRepository;

	    @Override
		public Cart getCart() {
	        return cart;
	    }

	    @Override
		public void addProduct(Long productId, int quantity) {
	        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
	        CartItem cartItem = new CartItem(product.getProductId(), product.getName(), quantity, product.getUnitPrice(), product);
	        cart.addItem(cartItem);
	    }

	    @Override
		public void removeCart(Long cartItemId) {
	        cart.removeItem(cartItemId);
	    }

	    @Override
		public double getTotal() {
	        return cart.getTotal();
	    }

		@Override
		public boolean equals(Object o) {
			return cart.equals(o);
		}

		@Override
		public List<CartItem> getItems() {
			return cart.getItems();
		}

		@Override
		public int hashCode() {
			return cart.hashCode();
		}

		@Override
		public void setItems(List<CartItem> items) {
			cart.setItems(items);
		}

		@Override
		public String toString() {
			return cart.toString();
		}

		@Override
		public void addItem(CartItem item) {
			cart.addItem(item);
		}

		@Override
		public void removeItem(Long productId) {
			cart.removeItem(productId);
		}
	    
}
