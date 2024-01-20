package com.example.Transactionservice.TransactionService.Wrapper;

import com.example.Transactionservice.TransactionService.Model.Transaction;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private int senderId;
    @NotNull
   private int receiverId;
    @Min(1)
    private double amount;
    @NotBlank
   private String purpose;
   public Transaction to(){
       return Transaction.builder().senderId(this.senderId).purpose(this.purpose).amount(this.amount).receiverId(this.receiverId).txnId(UUID.randomUUID().toString()).purpose(purpose).build();
   }
}
