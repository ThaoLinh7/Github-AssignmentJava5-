package edu.poly.shop.model;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto implements Serializable{
	 @NotEmpty(message = "Name is required")
	    private String name;

	    @NotEmpty(message = "Password is required")
	    private String password;

	    @NotEmpty(message = "Email is required")
	    @Email(message = "Email should be valid")
	    private String email;
	private boolean position;
	 private Boolean isEdit=false;
}
