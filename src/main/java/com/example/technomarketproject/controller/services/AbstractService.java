package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.Logger;
import com.example.technomarketproject.model.repositories.*;
import com.example.technomarketproject.model.entities.User;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public abstract class AbstractService {
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    protected ProductCharacteristicRepository productCharacteristicRepository;
    @Autowired
    protected OrderRepository orderRepository;
    @Autowired
    protected ReviewRepository reviewRepository;
    @Autowired
    protected SubcategoryRepository subcategoryRepository;
    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ModelMapper mapper;
    @Autowired
    protected ShoppingCartRepository shoppingCartRepository;
    @Autowired
    protected CategoryRepository categoryRepository;
    @Autowired
    protected SearchHistoryRepository searchHistoryRepository;
    @Autowired
    protected ProductImageRepository productImageRepository;
    @Autowired
    protected Logger logger;

    protected User findUserById(int id){
        User u = userRepository.findById(id).orElseThrow(() -> new FileNotFoundException("User with id " + id + " not found!"));
        return u;
    }
    public void sendEmail(String to, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("yoanpavlov12345@gmail.com");
        message.setTo(to);
        message.setText(body);
        message.setSubject(subject);

        javaMailSender.send(message);
        System.out.println("Mail sent to " + to + " successfully!");
    }

    public String generatePasswordResetToken() {
        SecureRandom random = new SecureRandom();
        byte[] token = new byte[20];
        random.nextBytes(token);
        return Base64.getEncoder().encodeToString(token);
    }
}
