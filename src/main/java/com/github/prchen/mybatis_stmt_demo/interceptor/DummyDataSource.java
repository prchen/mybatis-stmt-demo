package com.github.prchen.mybatis_stmt_demo.interceptor;

import lombok.AllArgsConstructor;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

@AllArgsConstructor
public class DummyDataSource implements DataSource {
    private final DataSource instance;

    @Override
    public Connection getConnection() throws SQLException {
        // Wrap the actual Connection with DummyConnection
        return new DummyConnection(instance.getConnection());
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        // Wrap the actual Connection with DummyConnection
        return new DummyConnection(instance.getConnection(username, password));
    }

    /* Delegate all other methods to the underlying instance */

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return instance.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        instance.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        instance.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return instance.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return instance.getParentLogger();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return instance.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return instance.isWrapperFor(iface);
    }
}
