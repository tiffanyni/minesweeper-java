package com.shoopy.minesweeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/Login")
    public String home() {
        return "login"; // This will look for a template named "login.html"
    }
}
