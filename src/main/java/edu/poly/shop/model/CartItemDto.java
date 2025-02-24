package edu.poly.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
	private Long cartItemId;
	private Long productId;
	private String name;
	private int quantity;
	private double unitPrice;
}
