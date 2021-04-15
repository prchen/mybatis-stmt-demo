package com.github.prchen.mybatis_stmt_demo.interceptor;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggerInterceptor extends Filter<ILoggingEvent> {
    @Override
    public FilterReply decide(ILoggingEvent iLoggingEvent) {
        if (iLoggingEvent.getMessage().contains("Parameters: ")) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("PreparedStatementLogger.statement.toString():");
            System.out.println(DummyPreparedStatement.getCurrentActualStatement());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        return FilterReply.NEUTRAL;
    }
}
