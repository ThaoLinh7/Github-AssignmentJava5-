package edu.poly.shop.model;

import java.io.Serializable;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto implements Serializable{
private Long customerId;
private String name;
private String email;
private String password;
private String phone;
private Date registeredDate;
private short status;

private Boolean isEdit = false;
}
