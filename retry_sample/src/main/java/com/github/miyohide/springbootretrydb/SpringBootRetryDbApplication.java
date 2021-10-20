package com.github.miyohide.springbootretrydb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class SpringBootRetryDbApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootRetryDbApplication.class, args);
    }

    @Autowired
    private MyService myService;

    @Override
    public void run(String... args) {
        myService.insert();
        myService.printRecords();
    }
}
