package com.shoopy.minesweeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MinesweeperApplication {

    @GetMapping("/helloworld")
    public String sayHello() {
        return "Hello My handsome AHUI";
    }

	public static void main(String[] args) {
		SpringApplication.run(MinesweeperApplication.class, args);
    }

}
