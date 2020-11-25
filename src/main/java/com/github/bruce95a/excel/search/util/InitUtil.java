package com.github.bruce95a.excel.search.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitUtil {
    private static final Logger logger = LoggerFactory.getLogger(InitUtil.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        logger.info("init database");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS item (" +
                "batch varchar(255)," +
                "industry varchar(255)," +
                "category varchar(255)," +
                "health varchar(255)," +
                "code varchar(255)," +
                "name varchar(255)," +
                "scope varchar(3000)," +
                "logout varchar(255)," +
                "status varchar(255) )");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS health_Ind (" +
                "heal_Cd varchar(255)," +
                "heal_Nm varchar(255)," +
                "Heal_Desc varchar(255)," +
                "industry_Cd varchar(255) )");
    }
}
