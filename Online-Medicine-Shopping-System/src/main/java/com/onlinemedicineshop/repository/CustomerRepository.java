package com.onlinemedicineshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlinemedicineshop.entity.Customer;
import com.onlinemedicineshop.security.model.User;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	Optional<User> getCustomerAsUserByEmail(String email);
	
}