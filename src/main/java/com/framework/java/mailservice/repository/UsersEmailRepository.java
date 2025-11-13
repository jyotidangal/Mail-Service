package com.framework.java.mailservice.repository;

import com.framework.java.mailservice.entity.UsersEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersEmailRepository extends JpaRepository<UsersEmail, Integer> {

    UsersEmail findByEmail(String email);
}
