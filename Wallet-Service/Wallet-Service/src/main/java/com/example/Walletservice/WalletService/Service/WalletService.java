package com.example.Walletservice.WalletService.Service;

import com.example.Walletservice.WalletService.Model.Wallet;
import com.example.Walletservice.WalletService.Repository.WalletRepo;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class WalletService {
    @Autowired
    KafkaTemplate<String,String>kafkaTemplate;
    @Autowired
    WalletRepo walletRepo;
    private final static String USER_CREATE_TOPIC="user-create-topic";
    private final static String TXN_TOPIC="txn-topic";
    private final static String WALLET_UPDATE_TOPIC="wallet-update-topic";
    @Value("${user.onboarding.amount}")
    private  Double onBoardingAmount;
    // Acting as Consumer wrt to User Service
    @KafkaListener(topics = {USER_CREATE_TOPIC}, groupId = "jdbl61_grp")
    public void createWallet(String message) throws  Exception{
        JSONObject jsonObject= (JSONObject) new JSONParser().parse(message);
        if(!jsonObject.containsKey("userId")){
            throw new Exception("userId is not present in User event");
        }
        int userId= ((Long) jsonObject.get("userId")).intValue();
        Wallet wallet=Wallet.builder().userId(userId).balance(onBoardingAmount).build();
        walletRepo.save(wallet);
    }
    // Acting as Producer and consumer both for Transactoin Service
    // Wallet as consumer wil consume the data from tracsaction like sender id ,recerver ,amount, transaction id
    // Now with that data the wallet will get updated
    //Wallet as Producer produces the data whether the transaction was successfull or not
    @KafkaListener(topics = {TXN_TOPIC},groupId = "jbdl27_grp")
    public void updateWallet(String message) throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(message);
         if(!jsonObject.containsKey("senderId")|| !jsonObject.containsKey("receiverId")|| !jsonObject.containsKey("amount")|| !jsonObject.containsKey("txnId"))
         {
             throw new Exception("Some of the details are not present in txn event");
         }
JSONObject walletUpdateEvent=new JSONObject();
       Integer senderId=((Long) jsonObject.get("senderId")).intValue();
         Integer receiverId=((Long) jsonObject.get("receiverId")).intValue();
         Double amount=(Double) jsonObject.get("amount");
         String txnId=(String) jsonObject.get("txnId");
        Wallet wallet=walletRepo.findByUserId(senderId);
        walletUpdateEvent.put("txnId",txnId);
if(wallet.getBalance()<amount){

    walletUpdateEvent.put("status","FAILED");
}
else{
    walletRepo.updateBalance(senderId,0-amount);
    walletRepo.updateBalance(receiverId,amount);
    walletUpdateEvent.put("status","SUCCESS");

}
kafkaTemplate.send(WALLET_UPDATE_TOPIC,walletUpdateEvent.toJSONString());

    }


}
