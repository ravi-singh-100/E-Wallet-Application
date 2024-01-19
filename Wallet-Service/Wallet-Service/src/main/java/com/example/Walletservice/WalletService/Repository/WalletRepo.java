package com.example.Walletservice.WalletService.Repository;

import com.example.Walletservice.WalletService.Model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepo extends JpaRepository<Wallet,Integer> {
    Wallet findByUserId(Integer userId);
    @Query("update Wallet w set w.balance = :balance where w.userId = :userId")
   void updateBalance(Integer userId,Double balance);
}
