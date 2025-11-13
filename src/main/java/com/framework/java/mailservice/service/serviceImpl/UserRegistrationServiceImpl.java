package com.framework.java.mailservice.service.serviceImpl;

import com.framework.java.mailservice.entity.UsersEmail;
import com.framework.java.mailservice.repository.UsersEmailRepository;
import com.framework.java.mailservice.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    @Autowired
    private UsersEmailRepository usersEmailRepository;

    @Override
    public String registerUser(UsersEmail usersEmail) {
        if (usersEmailRepository.findByEmail(usersEmail.getEmail()) != null) {
            return "❌ Email already exists!";
        } else {
            usersEmailRepository.save(usersEmail);
            return "✅ User registered successfully!";
        }
    }
}
