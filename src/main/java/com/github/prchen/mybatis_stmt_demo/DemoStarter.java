package com.github.prchen.mybatis_stmt_demo;

import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public class DemoStarter {

    public static void main(String... args) {
        List<Supplier<DemoTester.DemoTesterBuilder>> builderSuppliers = new ArrayList<>();
        List<Function<DemoMapper, String>> queryFunctions = new ArrayList<>();
        List<Class<? extends DataSource>> dataSourceTypes = Arrays.asList(
                org.apache.commons.dbcp2.BasicDataSource.class,
                org.apache.tomcat.jdbc.pool.DataSource.class,
                com.zaxxer.hikari.HikariDataSource.class);

        if (System.getProperty("mysql.jdbc-url") != null) {
            builderSuppliers.add(() -> DemoTester.builder()
                    .driverType(com.mysql.cj.jdbc.Driver.class)
                    .jdbcUrl(System.getProperty("mysql.jdbc-url"))
                    .username(System.getProperty("mysql.username"))
                    .password(System.getProperty("mysql.password")));
            queryFunctions.add(mapper -> mapper.echo("HELLO_WORD"));
        }

        if (System.getProperty("postgresql.jdbc-url") != null) {
            builderSuppliers.add(() -> DemoTester.builder()
                    .driverType(org.postgresql.Driver.class)
                    .jdbcUrl(System.getProperty("postgresql.jdbc-url"))
                    .username(System.getProperty("postgresql.username"))
                    .password(System.getProperty("postgresql.password")));
            queryFunctions.add(mapper -> mapper.echo("HELLO_WORD"));
        }

        if (System.getProperty("oracle.jdbc-url") != null) {
            builderSuppliers.add(() -> DemoTester.builder()
                    .driverType(oracle.jdbc.OracleDriver.class)
                    .jdbcUrl(System.getProperty("oracle.jdbc-url"))
                    .username(System.getProperty("oracle.username"))
                    .password(System.getProperty("oracle.password")));
            queryFunctions.add(mapper -> mapper.echoFromDual("HELLO_WORD"));
        }

        if (System.getProperty("mssql.jdbc-url") != null) {
            builderSuppliers.add(() -> DemoTester.builder()
                    .driverType(com.microsoft.sqlserver.jdbc.SQLServerDriver.class)
                    .jdbcUrl(System.getProperty("mssql.jdbc-url")));
            queryFunctions.add(mapper -> mapper.echo("HELLO_WORD"));
        }

        if (System.getProperty("h2", "0").equals("1")) {
            builderSuppliers.add(() -> DemoTester.builder()
                    .driverType(org.h2.Driver.class)
                    .jdbcUrl("jdbc:h2:mem:test"));
            queryFunctions.add(mapper -> mapper.echo("HELLO_WORD"));
        }

        if (System.getProperty("sqlite", "0").equals("1")) {
            builderSuppliers.add(() -> DemoTester.builder()
                    .driverType(org.sqlite.JDBC.class)
                    .jdbcUrl("jdbc:sqlite::memory:"));
            queryFunctions.add(mapper -> mapper.echo("HELLO_WORD"));
        }

        if (System.getProperty("hsqldb", "0").equals("1")) {
            builderSuppliers.add(() -> DemoTester.builder()
                    .driverType(org.hsqldb.jdbc.JDBCDriver.class)
                    .jdbcUrl("jdbc:hsqldb:mem:test")
                    .initSql("CREATE TABLE IF NOT EXISTS dual (id INTEGER)"));
            queryFunctions.add(mapper -> mapper.echoFromDual("HELLO_WORD"));
        }

        // Run all [Driver x DataSource] cases
        for (int i = 0; i < builderSuppliers.size(); i++) {
            for (Class<? extends DataSource> dataSourceType : dataSourceTypes) {
                DemoTester tester = builderSuppliers.get(i).get()
                        .dataSourceType(dataSourceType)
                        .build();
                tester.testQuery(queryFunctions.get(i));
            }
        }
    }
}
