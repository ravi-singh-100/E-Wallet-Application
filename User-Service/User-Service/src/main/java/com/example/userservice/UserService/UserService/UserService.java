package com.example.userservice.UserService.UserService;

import com.example.userservice.UserService.Model.User;
import com.example.userservice.UserService.Repo.UserRepo;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class UserService {
    private final static String USER_CREATE_TOPIC="user-create-topic";
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private UserRepo userRepo;
    public User createUser(User user) {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("userId",user.getId());
        jsonObject.put("userEmail",user.getEmail());
        jsonObject.put("userPhoneNumber",user.getPhoneNumber());
        kafkaTemplate.send(USER_CREATE_TOPIC,jsonObject.toJSONString());
        return userRepo.save(user);
    }

    public User getUserById(int id) {
        return userRepo.findById(id).get();
    }
}
