package com.shoopy.minesweeper.service;

import com.shoopy.minesweeper.dto.LoginRequest;
import com.shoopy.minesweeper.dto.RegisterRequest;
import com.shoopy.minesweeper.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    //responsibilities:
    //1. register new users
    //2. check if name already exists
    //3. hash password
    //4. verify password during login
    //5. find a user by username

    //store users in memory for now, later we can switch to a database
    private final Map<String, User> users = new HashMap<>(); // username string -> User object
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean register(RegisterRequest request) {
        String username = request.getUsername();

        //username needs to be unique, so check if it already exists before registering
        if (users.containsKey(request.getUsername())) {
            return false;
        }

        //hash the password and store the user
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(username, hashedPassword);
        users.put(username, user);
        return true;
    }

    public boolean login(LoginRequest request) {
        User user = users.get(request.getUsername());

        //user needs to exist in order to log in
        if (!users.containsKey(request.getUsername())) {
            return false;
        }

        //verify password matches the stored hash
        return passwordEncoder.matches(request.getPassword(), user.getPasswordHash());

    }

    public User findByUsername(String username) {
        return users.get(username);
    }

    // For development/debugging only
    public Map<String, User> getAllUsers() {
        return users;
    }



}
