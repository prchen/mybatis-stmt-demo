# Mybatis Stmt Demo

[MyBatis PR 2226](https://github.com/mybatis/mybatis-3/pull/2226)

A demonstration project used to check whether JDBC PreparedStatement implementations' `toString` output is useful or
not.

**Quick Start**

```bash
# Build demo.jar
git clone https://github.com/prchen/mybatis-stmt-demo.git
cd mybatis-stmt-demo
./mvnw clean package

# Use domo.jar to verify compatibility of MySQL
java \
  -Dmysql.jdbc-url='jdbc:mysql://HOST:PORT/DATABASE' \
  -Dmysql.username='USERNAME' \
  -Dmysql.password='PASSWORD' \
  -jar target/demo.jar

# Use demo.jar to verify compatibility of PostgreSQL
java \
  -Dpostgresql.jdbc-url='jdbc:postgresql://HOST:PORT/DATABASE' \
  -Dpostgresql.username='USERNAME' \
  -Dpostgresql.password='PASSWORD' \
  -jar target/demo.jar

# Use demo.jar to verify compatibility of Oracle
java \
  -Doracle.jdbc-url='jdbc:oracle:thin:@HOST:PORT/SERVICE' \
  -Doracle.username='USERNAME' \
  -Doracle.password='PASSWORD' \
  -jar target/demo.jar

# Use demo.jar to verify compatibility of MSSQL
java \
  -Dmssql.jdbc-url='jdbc:sqlserver://HOST:PORT;databaseName=DATABASE;user=USERNAME;password=PASSWORD' \
  -jar target/demo.jar

# Use demo.jar to verify compatibility of H2
java -Dh2=1 -jar target/demo.jar

# Use demo.jar to verify compatibility of SQLite
java -Dsqlite=1 -jar target/demo.jar

# Use demo.jar to verify compatibility of HSQLDB
java -Dhsqldb=1 -jar target/demo.jar

```

## Testing Equipments

| Dependency | Installation |
| ---- | ---- |
| JDK 8 | local machine |
| MySQL 5.7 | `docker pull mysql:5.7` |
| PostgreSQL 13.2 | `docker pull postgres:13.2` |
| Oracle XE 11g | `docker pull oracleinanutshell/oracle-xe-11g:latest` |
| SQL Server 2019 | `docker pull mcr.microsoft.com/mssql/server:2019-latest` |

## Test Result

**DataSource Compatibility**

| DataSource | Compatible | Implementation |
| ---- | ---- | ---- |
| CommonsDBCP2 2.2.8 | YES | Using original `PreparedStatement` |
| TomcatJDBC 9.0.44 | YES | Using wrapped `PreparedStatement` |
| HikariCP 3.4.5 | YES | Using wrapped `PreparedStatement` |

**JDBC Driver Compatibility**

| Database | Driver | Readable | Executable | 
| ---- | ---- | ---- | ---- |
| MySQL 5.7 | mysql:mysql-connector-java:8.0.23 | YES | YES |
| PostgreSQL 13.2 | org.postgresql:postgresql:42.2.19 | YES | YES |
| Oracle XE 11g | com.oracle.ojdbc:ojdbc8:19.3.0.0 | NO | NO |
| MSSQL 2019 | com.microsoft.sqlserver:mssql-jdbc:8.4.1.jre8 | NO | NO |
| H2 1.4.200 | com.h2database:h2:1.4.200 | YES | NO |
| SQLite 3.32.3.3 | org.xerial:sqlite-jdbc:3.32.3.3 | NO | NO |
| HSQLDB 2.5.1 | org.hsqldb:hsqldb:2.5.1 | YES | NO |
