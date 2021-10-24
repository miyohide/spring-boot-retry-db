package com.github.miyohide.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class MyService {
    private static final Logger log =
            LoggerFactory.getLogger(MyService.class);
    @Value("${app.records.num:100}")
    private int RECORDS_NUM;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Retryable(maxAttempts = 12, backoff = @Backoff(delay = 5_000, maxDelay = 10_000))
    @Transactional
    public void insert() {
        log.info("Start insert method...");
        for (int i = 0; i < RECORDS_NUM; i++) {
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
        log.info("End insert method...");
    }

    public void printRecords() {
        log.info("Querying for customer records where first_name = 'Josh%': ");
        jdbcTemplate.query("SELECT id, first_name, last_name, created_at FROM customers WHERE first_name LIKE ? ORDER BY id",
                        (rs, rowNum) -> new Customer(
                                rs.getLong("id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getObject("created_at", OffsetDateTime.class)
                        ),
                        new Object[] { "Josh%" })
                .forEach(customer -> log.info(customer.toString()));
        log.info("End printRecords method");
    }
}
