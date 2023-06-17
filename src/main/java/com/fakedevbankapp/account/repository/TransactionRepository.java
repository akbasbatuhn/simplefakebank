package com.fakedevbankapp.account.repository;

import com.fakedevbankapp.account.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
