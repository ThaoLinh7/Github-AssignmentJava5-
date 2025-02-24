package edu.poly.shop.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
	private Long orderDetailId;
	private Long orderId;
	private Long productId;
	private int quantity;
	private double unitPrice;
}
