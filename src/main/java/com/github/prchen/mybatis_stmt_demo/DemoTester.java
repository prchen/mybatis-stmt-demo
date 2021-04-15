package com.github.prchen.mybatis_stmt_demo;

import com.github.prchen.mybatis_stmt_demo.interceptor.DummyDataSource;
import lombok.Builder;
import lombok.Singular;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.function.Function;

@Slf4j
public class DemoTester {
    private final Class<? extends DataSource> dataSourceType;
    private final Class<? extends Driver> driverType;
    private final String jdbcUrl;
    private final String username;
    private final String password;
    private final SqlSessionFactory sqlSessionFactory;

    @Builder
    @SneakyThrows
    public DemoTester(Class<? extends DataSource> dataSourceType,
                      Class<? extends Driver> driverType,
                      String jdbcUrl, String username, String password,
                      @Singular List<String> initSqls) {
        this.dataSourceType = dataSourceType;
        this.driverType = driverType;
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
        // Use dummy JDBC objects to make actual statement instance accessible for logger interceptor
        DataSource actualDataSource = buildDataSource(dataSourceType, driverType, jdbcUrl, username, password);
        if (initSqls != null && !initSqls.isEmpty()) {
            try (Connection conn = actualDataSource.getConnection()) {
                for (String initializationSql : initSqls) {
                    try (PreparedStatement stmt = conn.prepareStatement(initializationSql)) {
                        stmt.executeUpdate();
                    }
                }
            }
        }
        DummyDataSource dummyDataSource = new DummyDataSource(actualDataSource);
        // Configure MyBatis
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dummyDataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(DemoMapper.class);
        // Build SqlSessionFactory
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    public void testQuery(Function<DemoMapper, String> messageQuery) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        DemoMapper mapper = sqlSession.getMapper(DemoMapper.class);
        try {
            log.info("================================================================================");
            log.info("Testing query method");
            log.info("DataSource Type: {}", dataSourceType.getName());
            log.info("Driver Type: {}", driverType.getName());
            log.info("Jdbc URL: {}", jdbcUrl);
            log.info("Username: {}", username);
            log.info("Password: {}", password);
            log.info("--------------------------------------------------------------------------------");
            String result = messageQuery.apply(mapper);
            log.info("--------------------------------------------------------------------------------");
            log.info("Query result: {}", result);
            log.info("================================================================================\n");
        } finally {
            sqlSession.close();
        }
    }

    private static DataSource buildDataSource(Class<? extends DataSource> dataSourceType,
                                              Class<? extends Driver> driverType,
                                              String jdbcUrl, String username, String password) {
        if (dataSourceType == org.apache.commons.dbcp2.BasicDataSource.class) {
            return buildBasicDataSource(driverType, jdbcUrl, username, password);
        } else if (dataSourceType == org.apache.tomcat.jdbc.pool.DataSource.class) {
            return buildTomcatDataSource(driverType, jdbcUrl, username, password);
        } else if (dataSourceType == com.zaxxer.hikari.HikariDataSource.class) {
            return buildHikariDataSource(driverType, jdbcUrl, username, password);
        } else {
            throw new IllegalArgumentException("Unknown dataSourceType: " + dataSourceType);
        }
    }

    private static DataSource buildHikariDataSource(Class<? extends Driver> driverType,
                                                    String jdbcUrl, String username, String password) {
        com.zaxxer.hikari.HikariConfig config = new com.zaxxer.hikari.HikariConfig();
        config.setDriverClassName(driverType.getName());
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        return new com.zaxxer.hikari.HikariDataSource(config);
    }

    private static DataSource buildBasicDataSource(Class<? extends Driver> driverType,
                                                   String jdbcUrl, String username, String password) {
        org.apache.commons.dbcp2.BasicDataSource dataSource = new org.apache.commons.dbcp2.BasicDataSource();
        dataSource.setDriverClassName(driverType.getName());
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    private static DataSource buildTomcatDataSource(Class<? extends Driver> driverType,
                                                    String jdbcUrl, String username, String password) {
        PoolProperties poolProperties = new PoolProperties();
        poolProperties.setDriverClassName(driverType.getName());
        poolProperties.setUrl(jdbcUrl);
        poolProperties.setUsername(username);
        poolProperties.setPassword(password);
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setPoolProperties(poolProperties);
        return dataSource;
    }
}
