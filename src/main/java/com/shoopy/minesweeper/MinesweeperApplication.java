package com.shoopy.minesweeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@SpringBootApplication
@RestController
public class MinesweeperApplication {

    private ArrayList<String> messages = new ArrayList<>();

    @PostMapping("/tellShoopy")
    public String tellShoopy(@RequestBody String message) {
        messages.add(message);
        return "Shoopy appreciates your message!!! Your message: " + message;
    }

    @GetMapping("/getShoopy")
    public String sayHello() {
        StringBuilder ans = new StringBuilder();
        for (String s : messages) {
            ans.append(s).append(" ");
        }
        return ans.toString();
    }

	public static void main(String[] args) {
		SpringApplication.run(MinesweeperApplication.class, args);
    }

}
