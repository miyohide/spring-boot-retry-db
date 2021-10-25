package com.github.miyohide.non_retry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NonRetryApplication implements CommandLineRunner {
    @Autowired
    private MyService myService;

    public static void main(String[] args) {
        SpringApplication.run(NonRetryApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        myService.insert();
        myService.printRecords();
    }
}
