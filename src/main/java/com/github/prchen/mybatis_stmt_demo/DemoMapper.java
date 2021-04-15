package com.github.prchen.mybatis_stmt_demo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DemoMapper {
    @Select("SELECT #{text}")
    String echo(@Param("text") String text);

    @Select("SELECT #{text} FROM DUAL")
    String echoFromDual(@Param("text") String text);
}
