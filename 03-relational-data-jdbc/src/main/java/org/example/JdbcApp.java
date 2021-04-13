package org.example;

import org.example.pojo.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Types;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class JdbcApp {

    private static final Logger log = LoggerFactory.getLogger(JdbcApp.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(JdbcApp.class, args);
    }

    @Bean
    public CommandLineRunner runner() {
        return args -> {
            log.info("creating table");
            jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
            jdbcTemplate.execute("CREATE TABLE customers(id SERIAL, " +
                    "first_name VARCHAR(255), last_name VARCHAR(255))");

            //准备数据
            List<Object[]> list = Stream.of("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long")
                    .map(name -> name.split(" "))
                    .collect(Collectors.toList());
            list.forEach(name ->
                    log.info("Inserting customer record for {} {}", name[0], name[1]));

            //插入数据
            jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?, ?)", list);

            // 查询数据
            jdbcTemplate.query("SELECT id, first_name, last_name FROM customers WHERE first_name=?"
                    , new Object[]{"Josh"}, new int[]{Types.VARCHAR}
                    , (rs, rowNum) -> new Customer(rs.getLong("id"),
                            rs.getString("first_name"), rs.getString("last_name")))
                    .forEach(customer -> log.info(customer.toString()));
        };
    }
}
