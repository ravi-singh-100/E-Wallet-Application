package com.example.Transactionservice.TransactionService.Model;

import com.example.Transactionservice.TransactionService.Enum.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String txnId;
    private int senderId;
    private int receiverId;
    private double amount;
    private String purpose;
    @Enumerated(value = EnumType.STRING)
    private StatusEnum status;
    @CreationTimestamp
    private Date createdOn;
    @UpdateTimestamp
    private Date updatedOn;
}
