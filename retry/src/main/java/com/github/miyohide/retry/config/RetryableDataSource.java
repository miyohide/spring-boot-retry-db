package com.github.miyohide.retry.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class RetryableDataSource extends AbstractDataSource {
    private static final Logger log =
            LoggerFactory.getLogger(RetryableDataSource.class);

    private final DataSource dataSource;

    public RetryableDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // AzureのRDBMSの再接続は5秒以上の間隔を空けて、最大60秒待つ必要がある。
    // see. https://docs.microsoft.com/ja-jp/azure/azure-sql/database/troubleshoot-common-connectivity-issues
    // このことから、5秒から10秒の間で最大12回リトライする
    @Override
    @Retryable(maxAttempts = 12, backoff = @Backoff(delay = 5_000, maxDelay = 10_000))
    public Connection getConnection() throws SQLException {
        log.info("getting connection ...");
        return dataSource.getConnection();
    }

    // AzureのRDBMSの再接続は5秒以上の間隔を空けて、最大60秒待つ必要がある。
    // see. https://docs.microsoft.com/ja-jp/azure/azure-sql/database/troubleshoot-common-connectivity-issues
    // このことから、5秒から10秒の間で最大12回リトライする
    @Override
    @Retryable(maxAttempts = 12, backoff = @Backoff(delay = 5_000, maxDelay = 10_000))
    public Connection getConnection(String username, String password) throws SQLException {
        log.info("getting connection by username and password...");
        return dataSource.getConnection(username, password);
    }
}
