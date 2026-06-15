package com.shoopy.minesweeper.service;

import com.shoopy.minesweeper.dto.LoginRequest;
import com.shoopy.minesweeper.dto.RegisterRequest;
import com.shoopy.minesweeper.model.User;
import com.shoopy.minesweeper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    // responsibilities: register, login, find user

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    // Persist users to database
    public boolean register(RegisterRequest request) {
        String username = request.getUsername();
        if (username == null || username.isBlank()) return false;

        if (userRepository.findByUsername(username).isPresent()) {
            return false; // already exists
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(username, hashedPassword);
        userRepository.save(user);
        return true;
    }

    public boolean login(LoginRequest request) {
        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
        if (optionalUser.isEmpty()) return false;
        User user = optionalUser.get();
        return passwordEncoder.matches(request.getPassword(), user.getPasswordHash());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    // For development/debugging only — return User
    public Map<String, User> getAllUsers() {
        List<User> users = userRepository.findAll();
        Map<String, User> map = new HashMap<>();
        for (User u : users) map.put(u.getUsername(), u);
        return map;
    }

    // For development/debugging only — delete all users
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }


}
