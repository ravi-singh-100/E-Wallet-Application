package com.example.Transactionservice.TransactionService.Repository;

import com.example.Transactionservice.TransactionService.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Integer> {
    Transaction findByIdTxnId(String txnId);
}
