package com.example.Walletservice.WalletService.Service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    private final static String USER_CREATE_TOPIC="user-create-topic";
    @KafkaListener(topics = USER_CREATE_TOPIC,groupId = "jbdl61")
    public void createWallet(String message) throws Exception {
        JSONObject jsonObject= (JSONObject) new JSONParser().parse(message);
        if(!jsonObject.containsKey("userId")){
            throw new Exception("userId is not present in User event");
        }
        int userId= (int) jsonObject.get("userId");
    }
}
