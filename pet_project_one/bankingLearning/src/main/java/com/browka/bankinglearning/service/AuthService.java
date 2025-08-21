package com.browka.bankinglearning.service;

import com.browka.bankinglearning.Requests.MailRequest;
import com.browka.bankinglearning.Requests.RegRequest;
import com.browka.bankinglearning.model.BankUser;
import com.browka.bankinglearning.model.Mail;
import com.browka.bankinglearning.repository.BankRepo;
import com.browka.bankinglearning.repository.MailRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private BankRepo bankRepo;
    @Autowired
    private MailRepo mailRepo;
    @Autowired
    private AuthenticationManager authenticationManager;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
    @Autowired
    private JWTService jwtService;
    @Transactional
    public Object register(RegRequest request) {
        if (bankRepo.findByUsername(request.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("пользователь уже существует");
        }
        BankUser bankUser = new BankUser();
        bankUser.setUsername(request.getUsername());
        bankUser.setRole(BankUser.roles.USER);
        bankUser.setBalance(0);
        bankUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        return bankRepo.save(bankUser);
    }

    public String verify(RegRequest bankUser) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(bankUser.getUsername(), bankUser.getPassword()));
        if (auth.isAuthenticated()) {
            return jwtService.generateToken(bankUser.getUsername());
        }
        else return "FAIL";
    }
    @Transactional
    public String ChangeRole(String username) {
        BankUser user = bankRepo.findByUsername(username);
        user.setRole(BankUser.roles.ADMIN);
        bankRepo.save(user);
        return "SUCCESS";
    }
    @Transactional
    public String addmail(String username, String mail) {
        BankUser user = bankRepo.findByUsername(username);
        if (user.getMail() != null) {
            return "You already have an email address";
        }
        if (mailRepo.findByEmail(mail) != null) {
            return "This email address is already in use";
        }
        Mail mailObj = new Mail();
        mailObj.setEmail(mail);
        mailObj.setBankUser(user);
        mailRepo.save(mailObj);
        return "SUCCESS";
    }
}
