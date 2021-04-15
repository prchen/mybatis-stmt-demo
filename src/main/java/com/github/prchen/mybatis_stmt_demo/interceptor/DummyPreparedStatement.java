package com.github.prchen.mybatis_stmt_demo.interceptor;

import com.zaxxer.hikari.pool.ProxyStatement;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;

public class DummyPreparedStatement implements PreparedStatement {
    private static final ThreadLocal<PreparedStatement> currentInstance = new ThreadLocal<>();
    private final PreparedStatement instance;

    public static PreparedStatement getCurrentActualStatement() {
        return currentInstance.get();
    }

    public DummyPreparedStatement(PreparedStatement instance) {
        this.instance = instance;
        currentInstance.set(this);
    }

    /**
     * Format toString output with underlying instance's toString() result
     *
     * @return toString() result
     * @see ProxyStatement#toString()
     */
    @Override
    public String toString() {
        String delegateToString = this.instance.toString();
        return this.getClass().getSimpleName() + '@' + System.identityHashCode(this) + " wrapping " + delegateToString;
    }

    @Override
    public void close() throws SQLException {
        // Clear thead local cache when statement closed
        currentInstance.remove();
        instance.close();
    }

    /* Delegate all other methods to the underlying instance */

    @Override
    public ResultSet executeQuery() throws SQLException {
        return instance.executeQuery();
    }

    @Override
    public int executeUpdate() throws SQLException {
        return instance.executeUpdate();
    }

    @Override
    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        instance.setNull(parameterIndex, sqlType);
    }

    @Override
    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        instance.setBoolean(parameterIndex, x);
    }

    @Override
    public void setByte(int parameterIndex, byte x) throws SQLException {
        instance.setByte(parameterIndex, x);
    }

    @Override
    public void setShort(int parameterIndex, short x) throws SQLException {
        instance.setShort(parameterIndex, x);
    }

    @Override
    public void setInt(int parameterIndex, int x) throws SQLException {
        instance.setInt(parameterIndex, x);
    }

    @Override
    public void setLong(int parameterIndex, long x) throws SQLException {
        instance.setLong(parameterIndex, x);
    }

    @Override
    public void setFloat(int parameterIndex, float x) throws SQLException {
        instance.setFloat(parameterIndex, x);
    }

    @Override
    public void setDouble(int parameterIndex, double x) throws SQLException {
        instance.setDouble(parameterIndex, x);
    }

    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        instance.setBigDecimal(parameterIndex, x);
    }

    @Override
    public void setString(int parameterIndex, String x) throws SQLException {
        instance.setString(parameterIndex, x);
    }

    @Override
    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        instance.setBytes(parameterIndex, x);
    }

    @Override
    public void setDate(int parameterIndex, Date x) throws SQLException {
        instance.setDate(parameterIndex, x);
    }

    @Override
    public void setTime(int parameterIndex, Time x) throws SQLException {
        instance.setTime(parameterIndex, x);
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        instance.setTimestamp(parameterIndex, x);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        instance.setAsciiStream(parameterIndex, x, length);
    }

    @Override
    @Deprecated
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
        instance.setUnicodeStream(parameterIndex, x, length);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        instance.setBinaryStream(parameterIndex, x, length);
    }

    @Override
    public void clearParameters() throws SQLException {
        instance.clearParameters();
    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        instance.setObject(parameterIndex, x, targetSqlType);
    }

    @Override
    public void setObject(int parameterIndex, Object x) throws SQLException {
        instance.setObject(parameterIndex, x);
    }

    @Override
    public boolean execute() throws SQLException {
        return instance.execute();
    }

    @Override
    public void addBatch() throws SQLException {
        instance.addBatch();
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        instance.setCharacterStream(parameterIndex, reader, length);
    }

    @Override
    public void setRef(int parameterIndex, Ref x) throws SQLException {
        instance.setRef(parameterIndex, x);
    }

    @Override
    public void setBlob(int parameterIndex, Blob x) throws SQLException {
        instance.setBlob(parameterIndex, x);
    }

    @Override
    public void setClob(int parameterIndex, Clob x) throws SQLException {
        instance.setClob(parameterIndex, x);
    }

    @Override
    public void setArray(int parameterIndex, Array x) throws SQLException {
        instance.setArray(parameterIndex, x);
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return instance.getMetaData();
    }

    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        instance.setDate(parameterIndex, x, cal);
    }

    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        instance.setTime(parameterIndex, x, cal);
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        instance.setTimestamp(parameterIndex, x, cal);
    }

    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
        instance.setNull(parameterIndex, sqlType, typeName);
    }

    @Override
    public void setURL(int parameterIndex, URL x) throws SQLException {
        instance.setURL(parameterIndex, x);
    }

    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        return instance.getParameterMetaData();
    }

    @Override
    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        instance.setRowId(parameterIndex, x);
    }

    @Override
    public void setNString(int parameterIndex, String value) throws SQLException {
        instance.setNString(parameterIndex, value);
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        instance.setNCharacterStream(parameterIndex, value, length);
    }

    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        instance.setNClob(parameterIndex, value);
    }

    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        instance.setClob(parameterIndex, reader, length);
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        instance.setBlob(parameterIndex, inputStream, length);
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        instance.setNClob(parameterIndex, reader, length);
    }

    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        instance.setSQLXML(parameterIndex, xmlObject);
    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
        instance.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        instance.setAsciiStream(parameterIndex, x, length);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        instance.setBinaryStream(parameterIndex, x, length);
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        instance.setCharacterStream(parameterIndex, reader, length);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        instance.setAsciiStream(parameterIndex, x);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        instance.setBinaryStream(parameterIndex, x);
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        instance.setCharacterStream(parameterIndex, reader);
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        instance.setNCharacterStream(parameterIndex, value);
    }

    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        instance.setClob(parameterIndex, reader);
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        instance.setBlob(parameterIndex, inputStream);
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        instance.setNClob(parameterIndex, reader);
    }

    @Override
    public void setObject(int parameterIndex, Object x, SQLType targetSqlType, int scaleOrLength) throws SQLException {
        instance.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
    }

    @Override
    public void setObject(int parameterIndex, Object x, SQLType targetSqlType) throws SQLException {
        instance.setObject(parameterIndex, x, targetSqlType);
    }

    @Override
    public long executeLargeUpdate() throws SQLException {
        return instance.executeLargeUpdate();
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        return instance.executeQuery(sql);
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        return instance.executeUpdate(sql);
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        return instance.getMaxFieldSize();
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {
        instance.setMaxFieldSize(max);
    }

    @Override
    public int getMaxRows() throws SQLException {
        return instance.getMaxRows();
    }

    @Override
    public void setMaxRows(int max) throws SQLException {
        instance.setMaxRows(max);
    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        instance.setEscapeProcessing(enable);
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        return instance.getQueryTimeout();
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        instance.setQueryTimeout(seconds);
    }

    @Override
    public void cancel() throws SQLException {
        instance.cancel();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return instance.getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
        instance.clearWarnings();
    }

    @Override
    public void setCursorName(String name) throws SQLException {
        instance.setCursorName(name);
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        return instance.execute(sql);
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return instance.getResultSet();
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return instance.getUpdateCount();
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        return instance.getMoreResults();
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        instance.setFetchDirection(direction);
    }

    @Override
    public int getFetchDirection() throws SQLException {
        return instance.getFetchDirection();
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        instance.setFetchSize(rows);
    }

    @Override
    public int getFetchSize() throws SQLException {
        return instance.getFetchSize();
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        return instance.getResultSetConcurrency();
    }

    @Override
    public int getResultSetType() throws SQLException {
        return instance.getResultSetType();
    }

    @Override
    public void addBatch(String sql) throws SQLException {
        instance.addBatch(sql);
    }

    @Override
    public void clearBatch() throws SQLException {
        instance.clearBatch();
    }

    @Override
    public int[] executeBatch() throws SQLException {
        return instance.executeBatch();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return instance.getConnection();
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        return instance.getMoreResults(current);
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        return instance.getGeneratedKeys();
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return instance.executeUpdate(sql, autoGeneratedKeys);
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return instance.executeUpdate(sql, columnIndexes);
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        return instance.executeUpdate(sql, columnNames);
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        return instance.execute(sql, autoGeneratedKeys);
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        return instance.execute(sql, columnIndexes);
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        return instance.execute(sql, columnNames);
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        return instance.getResultSetHoldability();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return instance.isClosed();
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        instance.setPoolable(poolable);
    }

    @Override
    public boolean isPoolable() throws SQLException {
        return instance.isPoolable();
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        instance.closeOnCompletion();
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return instance.isCloseOnCompletion();
    }

    @Override
    public long getLargeUpdateCount() throws SQLException {
        return instance.getLargeUpdateCount();
    }

    @Override
    public void setLargeMaxRows(long max) throws SQLException {
        instance.setLargeMaxRows(max);
    }

    @Override
    public long getLargeMaxRows() throws SQLException {
        return instance.getLargeMaxRows();
    }

    @Override
    public long[] executeLargeBatch() throws SQLException {
        return instance.executeLargeBatch();
    }

    @Override
    public long executeLargeUpdate(String sql) throws SQLException {
        return instance.executeLargeUpdate(sql);
    }

    @Override
    public long executeLargeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return instance.executeLargeUpdate(sql, autoGeneratedKeys);
    }

    @Override
    public long executeLargeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return instance.executeLargeUpdate(sql, columnIndexes);
    }

    @Override
    public long executeLargeUpdate(String sql, String[] columnNames) throws SQLException {
        return instance.executeLargeUpdate(sql, columnNames);
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
