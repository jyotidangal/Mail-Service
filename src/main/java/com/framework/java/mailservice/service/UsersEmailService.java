package com.framework.java.mailservice.service;

import com.framework.java.mailservice.entity.UsersEmail;
import java.util.List;

public interface UsersEmailService {

    // 🔹 Fetch all emails
    List<UsersEmail> getAllEmails();

    // 🔹 Fetch one user by email
    UsersEmail getUserByEmail(String email);

    // 🔹 Fetch few emails (e.g., limit 3)
    List<UsersEmail> getFewEmails(int limit);


}
