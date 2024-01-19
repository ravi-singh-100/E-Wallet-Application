package com.example.Notificationservice.NotificationService.Service;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final static String WALLET_UPDATE_TOPIC="wallet-update-topic";
    @KafkaListener(topics = WALLET_UPDATE_TOPIC)
    public void notify(String message) throws ParseException {
        JSONObject jsonObject= (JSONObject) new JSONParser().parse(message);
        String txnId=(String) jsonObject.get("txnId");
        String senderEmail=(String) jsonObject.get("senderEmail");
        String receiverEmail=(String) jsonObject.get("receiverEmail");
        Double amount=(Double) jsonObject.get("amount");
        
    }
}
