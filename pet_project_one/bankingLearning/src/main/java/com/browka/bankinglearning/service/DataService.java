package com.browka.bankinglearning.service;

import com.browka.bankinglearning.model.BankUser;
import com.browka.bankinglearning.model.BankUserDetails;
import com.browka.bankinglearning.repository.BankRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DataService implements UserDetailsService {
    @Autowired
    private BankRepo bankRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BankUser bankUser = bankRepo.findByUsername(username);
        if (bankUser == null) {
            throw new UsernameNotFoundException(username);
        } else {return new BankUserDetails(bankUser);
        }
    }
}
