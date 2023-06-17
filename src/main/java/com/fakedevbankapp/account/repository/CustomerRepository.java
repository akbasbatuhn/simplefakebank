package com.fakedevbankapp.account.repository;

import com.fakedevbankapp.account.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
