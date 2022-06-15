package com.example.lockerSecurity.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.lockerSecurity.Repository.UserRepository;
import com.example.lockerSecurity.entity.User;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
    private JavaMailSender javaMailSender;

	public User getUserByUsername(String username,long secretCode) {
		User user = userRepository.findByUsername(username);
		if(user.getSecretCode() != secretCode) {
			user= new User();
		}
		
		return user;
		
	}

	public String sendOtp(String username) {
		// TODO Auto-generated method stub
		User user = userRepository.findByUsername(username);
		SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(user.getEmail());
        msg.setSubject("Secure Locker System ");
        Random rand = new Random();
        String otp = String.format("%04d", rand.nextInt(10000));
        msg.setText("We have generated OTP on your request to open locker, <strong>OTP</strong> :" + otp);
        javaMailSender.send(msg);
		return otp;
	}
	
	public User saveUser(User user) {
		return userRepository.save(user);
		
	}

	public String getEyePatternByUsername(String username) {
		User user = userRepository.findByUsername(username);
		return user.getEyePattern();
	}
}
