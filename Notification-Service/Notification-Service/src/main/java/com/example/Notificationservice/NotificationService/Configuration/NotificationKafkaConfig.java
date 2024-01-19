package com.example.Notificationservice.NotificationService.Configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class NotificationKafkaConfig {
    Properties getConsumerConfig(){
        Properties properties=new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
        return properties;
    }
    @Bean
    ConsumerFactory<String,String>getConsumerFactory(){
        return new DefaultKafkaConsumerFactory(getConsumerConfig());
    }
    @Bean
    JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl javaMailSender=new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587); // smtp port
        javaMailSender.setUsername("ravibajethapractice@gmail.com");
        javaMailSender.setPassword("");
        Properties properties=javaMailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable",true);
        properties.put("debug.enable",true);
        return javaMailSender;
    }
    @Bean
    SimpleMailMessage getSimpleMail(){
        return new SimpleMailMessage();
    }
}
