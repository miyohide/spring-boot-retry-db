package com.github.miyohide.springbootretrydb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootRetryDbApplication implements CommandLineRunner {
    @Autowired
    private MyService myService;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRetryDbApplication.class, args);
    }

    @Override
    public void run(String... args) {
        myService.insert();
        myService.printRecords();
    }
}
