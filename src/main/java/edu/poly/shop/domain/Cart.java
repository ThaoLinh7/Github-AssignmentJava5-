package edu.poly.shop.domain;


import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Cart {
    private List<CartItem> items = new ArrayList<>();

    public void addItem(CartItem item) {
        items.add(item);
    }

    public void removeItem(Long productId) {
        items.removeIf(item -> item.getProduct().equals(productId));
    }

    public double getTotal() {
        return items.stream().mapToDouble(item -> item.getUnitPrice() * item.getQuantity()).sum();
    }
}
