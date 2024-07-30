package edu.poly.shop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.poly.shop.domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	List<Customer> findByNameContaining(String name);
	Page<Customer> findByNameContaining(String name,Pageable pageable);
	
}
