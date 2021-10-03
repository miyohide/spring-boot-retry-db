package com.github.miyohide.springbootretrydb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.OffsetDateTime;

@SpringBootApplication
public class SpringBootRetryDbApplication implements CommandLineRunner {
    private static final Logger log =
            LoggerFactory.getLogger(SpringBootRetryDbApplication.class);
    @Autowired
    JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRetryDbApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("Start command line app...");
        for (int i = 0; i < 1_000; i++) {
            String first_name = String.format("Josh%05d", i);
            String last_name = String.format("hogehoge%05d", i);
            log.info("Insert record. i = [" + i + "], first_name = [" + first_name + "], last_name = [" + last_name + "]");
            jdbcTemplate.update("INSERT INTO customers(first_name, last_name) VALUES (?, ?)", first_name, last_name);
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                log.info("Thread.sleep occurs InterruptedException. i = [" + i + "]");
            }
        }
        log.info("Querying for customer records where first_name = 'Josh%': ");
        jdbcTemplate.query("SELECT id, first_name, last_name, created_at FROM customers WHERE first_name LIKE ?",
                (rs, rowNum) -> new Customer(
                        rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getObject("created_at", OffsetDateTime.class)
                ),
                        new Object[] { "Josh%" })
                        .forEach(customer -> log.info(customer.toString()));
        log.info("End command line app");
    }
}
