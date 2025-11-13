package com.framework.java.mailservice.service.serviceImpl;

import com.framework.java.mailservice.entity.UsersEmail;
import com.framework.java.mailservice.repository.UsersEmailRepository;
import com.framework.java.mailservice.service.UsersEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersEmailServiceImpl implements UsersEmailService {

    @Autowired
    private UsersEmailRepository usersEmailRepository;

    @Override
    public List<UsersEmail> getAllEmails() {
        return usersEmailRepository.findAll();
    }

    @Override
    public UsersEmail getUserByEmail(String email) {
        return usersEmailRepository.findByEmail(email);
    }

    @Override
    public List<UsersEmail> getFewEmails(int limit) {
        List<UsersEmail> allUsers = usersEmailRepository.findAll();
        return allUsers.stream().limit(limit).toList();
    }



}
