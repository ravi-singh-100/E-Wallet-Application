package com.example.Transactionservice.TransactionService.Service;

import com.example.Transactionservice.TransactionService.Enum.StatusEnum;
import com.example.Transactionservice.TransactionService.Model.Transaction;
import com.example.Transactionservice.TransactionService.Repository.TransactionRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TransactionService {
    private final static String TXN_COMPLETE_TOPIC="txn-complete";
    private final static String TXN_TOPIC="txn-topic";
    private final static String WALLET_UPDATE_TOPIC="wallet-update-topic";
    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    KafkaTemplate<String,String>kafkaTemplate;
    @Autowired
    RestTemplate restTemplate;
    public ResponseEntity<String> createTxn(Transaction transaction)  throws JsonProcessingException {
        transaction.setStatus(StatusEnum.PENDING);
        transactionRepo.save(transaction);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("senderId",transaction.getSenderId());
        jsonObject.put("receiverId",transaction.getReceiverId());
        jsonObject.put("txnId",transaction.getTxnId());
        jsonObject.put("amount",transaction.getAmount());
        kafkaTemplate.send(TXN_TOPIC,jsonObject.toJSONString());
        return new ResponseEntity<>(transaction.getTxnId(), HttpStatusCode.valueOf(201));
    }
    @KafkaListener(topics = WALLET_UPDATE_TOPIC,groupId = "jbdl61_grp")
    public void updateTxn(String message) throws ParseException {
JSONObject jsonObject= (JSONObject) new JSONParser().parse(message);
String txnId=(String)jsonObject.get("txnId");
String status=(String) jsonObject.get("status");
StatusEnum statusEnum;
if(status.equals("FAILED")){
    statusEnum=StatusEnum.FAILED;
}else{
    statusEnum=StatusEnum.SUCCESS;
}
Transaction transaction=transactionRepo.findByTxnId(txnId);
transaction.setStatus(statusEnum);
transactionRepo.save(transaction);
Integer senderId=transaction.getSenderId();
Integer receiverId=transaction.getReceiverId();

JSONObject txnComplete=new JSONObject();

        // HTTP WAY TO COMMUNICATING BETWEEEN SERVICES
       JSONObject sender= restTemplate.getForObject("http://localhost:9095/get-user?id="+senderId,JSONObject.class);
       JSONObject receiver=restTemplate.getForObject("http://localhost:9095/get-user?id="+receiverId,JSONObject.class);
         String senderEmail=(String) sender.get("email");
         String receiverEmail=(String)receiver.get("email");
         JSONObject txnEventComplete=new JSONObject();
         txnEventComplete.put("txnId",txnId);
         txnEventComplete.put("senderEmail",senderEmail);
         txnEventComplete.put("receiverEmail",receiverEmail);
         txnEventComplete.put("status",transaction.getStatus().name());
         txnEventComplete.put("amount",transaction.getAmount());
kafkaTemplate.send(TXN_COMPLETE_TOPIC,txnEventComplete.toJSONString());

    }
}
