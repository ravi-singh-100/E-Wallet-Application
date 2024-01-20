package com.example.Notificationservice.NotificationService.Service;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    SimpleMailMessage simpleMailMessage;
    @Autowired
    JavaMailSender javaMailSender;
    private final static String TXN_COMPLETE_TOPIC="txn-complete";
    @KafkaListener(topics = {TXN_COMPLETE_TOPIC},groupId = "jdbl61_grp")
    public void notify(String message) throws ParseException {
        JSONObject jsonObject= (JSONObject) new JSONParser().parse(message);
        String txnId=(String) jsonObject.get("txnId");
        String senderEmail=(String) jsonObject.get("senderEmail");
        String receiverEmail=(String) jsonObject.get("receiverEmail");
        Double amount=(Double) jsonObject.get("amount");
        String status=(String) jsonObject.get("status");

        simpleMailMessage.setText("Hi you transaction with id "+txnId +" is "+status);
        simpleMailMessage.setTo(senderEmail);
        simpleMailMessage.setSubject("Transaction Notification");

        simpleMailMessage.setFrom("ravibajethapractice@gmail.com");
        javaMailSender.send(simpleMailMessage);
        if(status.equals("SUCCESS")){
            simpleMailMessage.setText("Credit Amount : "+amount +" from "+senderEmail);
            simpleMailMessage.setTo(receiverEmail);
            javaMailSender.send(simpleMailMessage);
        }

    }
}
