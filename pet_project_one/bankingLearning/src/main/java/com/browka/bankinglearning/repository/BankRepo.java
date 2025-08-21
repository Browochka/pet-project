package com.browka.bankinglearning.repository;

import com.browka.bankinglearning.model.BankUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepo extends JpaRepository<BankUser, Integer> {
    BankUser findByUsername(String username);
    BankUser findById(int id);

}
