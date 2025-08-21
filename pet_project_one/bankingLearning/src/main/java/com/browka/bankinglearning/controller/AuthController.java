package com.browka.bankinglearning.controller;

import com.browka.bankinglearning.Requests.MailRequest;
import com.browka.bankinglearning.Requests.RegRequest;
import com.browka.bankinglearning.model.BankUser;
import com.browka.bankinglearning.model.Mail;
import com.browka.bankinglearning.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/register")
    public Object register(@RequestBody RegRequest request) {
        return authService.register(request);
    }
    @PostMapping("/login")
    public String login(@RequestBody RegRequest request) {
        return authService.verify(request);
    }
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/setrole")
    public String setrole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return authService.ChangeRole(username);
    }
    @PutMapping("/mail")
    public String addmail(@RequestBody MailRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return authService.addmail(username,request.getMail());
    }

}
