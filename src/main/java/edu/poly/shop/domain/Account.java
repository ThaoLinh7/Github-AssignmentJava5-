package edu.poly.shop.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account implements Serializable{
	@Id
	@Column(length=30)
	private String name;
	
	@Column(length=20)
	private String password;
	
	@Column(length=50)
    private String email;

    @Column
    private boolean position;
}
