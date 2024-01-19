package com.example.Transactionservice.TransactionService.Wrapper;

import com.example.Transactionservice.TransactionService.Model.Transaction;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionWrapper {
    @NotBlank
    private int senderId;
    @NotBlank
   private int receiverId;
    @Min(1)
    private double amount;
    @NotBlank
   private String purpose;
   public Transaction to(){
       return Transaction.builder().senderId(senderId).amount(amount).receiverId(receiverId).txnId(UUID.randomUUID().toString()).purpose(purpose).build();
   }
}
