package com.example.Transactionservice.TransactionService.Controller;

import com.example.Transactionservice.TransactionService.Service.TransactionService;
import com.example.Transactionservice.TransactionService.Wrapper.TransactionWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transaction")
    public ResponseEntity<String> createTxn(@Valid @RequestBody TransactionWrapper transactionWrapper)  {
       return transactionService.createTxn(transactionWrapper.to());

    }
}
