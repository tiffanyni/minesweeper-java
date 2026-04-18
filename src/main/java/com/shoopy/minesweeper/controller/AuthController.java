package com.shoopy.minesweeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/user-login")
    public String loginPage() {
        return "login"; // -> templates/login.html
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register"; // -> templates/register.html

    }
}
