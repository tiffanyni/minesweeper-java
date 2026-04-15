package com.shoopy.minesweeper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/helloworld").permitAll() // allow this endpoint
                        .anyRequest().authenticated()               // everything else locked
                )
                .formLogin(form -> form
                        .permitAll() // allow login page
                )
                .csrf(csrf -> csrf.disable()); // disable for now (simplifies testing)

        return http.build();
    }
}