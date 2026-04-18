package com.shoopy.minesweeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {

    @GetMapping("/game")
    public String game() {
        return "game"; // -> templates/game.html
    }

    //TODO: update so that it passes board generator's board into game.html
}
