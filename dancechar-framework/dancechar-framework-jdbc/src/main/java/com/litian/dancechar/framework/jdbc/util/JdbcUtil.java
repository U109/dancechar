package com.litian.dancechar.framework.jdbc.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * jdbc操作工具类
 *
 * @author tojson
 * @date 2022/10/14 16:20
 */
public class JdbcUtil {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private JdbcTemplate getJdbcTemplate(){
        return this.jdbcTemplate;
    }

    private NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(){
        return this.namedParameterJdbcTemplate;
    }

    public <T> List<T> queryList(String sql, RowMapper<T> rowMapper){
        return jdbcTemplate.query(sql, rowMapper);
    }


}
