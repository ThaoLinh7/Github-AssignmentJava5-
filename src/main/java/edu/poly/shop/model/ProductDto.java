package edu.poly.shop.model;

import java.io.Serializable;
import java.sql.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto implements Serializable{
	
private Long productId;
@NotEmpty
@Length(min=5)
private String name;
private int quantity;
private double unitPrice;
private String image;
private MultipartFile imageFile;
private String description;
private double discount;
private Date enteredDate;
private short status;
private Long categoryId;

private Boolean isEdit = false;
}
