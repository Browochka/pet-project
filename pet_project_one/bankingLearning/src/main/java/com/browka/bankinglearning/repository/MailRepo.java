package com.browka.bankinglearning.repository;

import com.browka.bankinglearning.model.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepo extends JpaRepository<Mail, Integer> {
    Mail findById(int id);
    Mail findByEmail(String email);
}
