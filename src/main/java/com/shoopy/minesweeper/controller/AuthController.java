package com.shoopy.minesweeper.controller;

import com.shoopy.minesweeper.dto.LoginRequest;
import com.shoopy.minesweeper.dto.RegisterRequest;
import com.shoopy.minesweeper.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    // Display login page
    @GetMapping("/user-login")
    public String loginPage() {
        return "login"; // -> templates/login.html
    }

    // Display register page
    @GetMapping("/register")
    public String registerPage() {
        return "register"; // -> templates/register.html
    }

    // API endpoint for login
    @PostMapping("/api/auth/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        if (authService.login(request)) {
            // Store username in session to mark user as logged in
            session.setAttribute("username", request.getUsername());
            response.put("success", true);
            response.put("message", "Login successful");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Invalid username or password");
            return ResponseEntity.status(401).body(response);
        }
    }

    // API endpoint for register
    @PostMapping("/api/auth/register")
    @ResponseBody
    public ResponseEntity<?> register(@RequestBody RegisterRequest request, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        if (authService.register(request)) {
            // Auto-login after successful registration
            session.setAttribute("username", request.getUsername());
            response.put("success", true);
            response.put("message", "Registration successful");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Username already exists");
            return ResponseEntity.status(400).body(response);
        }
    }

    // Debug endpoint - displays all registered users (REMOVE IN PRODUCTION)
    @GetMapping("/api/auth/debug/users")
    @ResponseBody
    public ResponseEntity<?> debugGetUsers() {
        Map<String, Object> response = new HashMap<>();
        response.put("totalUsers", authService.getAllUsers().size());
        response.put("users", authService.getAllUsers().keySet()); // Just usernames, not passwords
        return ResponseEntity.ok(response);
    }
}
