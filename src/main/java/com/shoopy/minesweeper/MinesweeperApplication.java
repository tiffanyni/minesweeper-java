package com.shoopy.minesweeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MinesweeperApplication {

    @PostMapping("/tellShoopy")
    public String createUser(@RequestBody String message) {
        return "Shoopy appreciates your message!!! Your message: " + message;
    }

    @GetMapping("/helloworld")
    public String sayHello() {
        return "Hello World";
    }

	public static void main(String[] args) {
		SpringApplication.run(MinesweeperApplication.class, args);
    }

}
