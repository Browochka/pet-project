package com.browka.bankinglearning.config;

import com.browka.bankinglearning.model.BankUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JWTFilter jwtFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return
                http.csrf(token ->token.disable()).
                        authorizeHttpRequests(authorizeRequests ->
                                authorizeRequests.requestMatchers("/register","/login")
                                        .permitAll().anyRequest().authenticated()).
                        httpBasic(Customizer.withDefaults()).
                        sessionManagement(sessionManagement ->
                                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                         addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();


    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authprov = new DaoAuthenticationProvider();
        authprov.setPasswordEncoder(new BCryptPasswordEncoder(10));
        authprov.setUserDetailsService(userDetailsService);
        return authprov;


    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {return config.getAuthenticationManager();
    }


}
