package edu.poly.shop.model;

import java.io.Serializable;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto implements Serializable{
	private Long orderId;
	private Date orderDate;
	private Long customerId;
	private double amount;
//	private String name;
	
	private Boolean isEdit = false;
}
