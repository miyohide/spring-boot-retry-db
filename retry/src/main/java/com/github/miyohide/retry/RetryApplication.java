package com.github.miyohide.retry;

import com.github.miyohide.springbootretrydb.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class RetryApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RetryApplication.class, args);
    }

    @Autowired
    private MyService myService;

    @Override
    public void run(String... args) throws Exception {
        myService.insert();
        myService.printRecords();
    }
}
