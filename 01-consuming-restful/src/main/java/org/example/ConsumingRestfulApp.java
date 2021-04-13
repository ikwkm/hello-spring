package org.example;

import org.example.pojo.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ConsumingRestfulApp {

    private static final Logger logger = LoggerFactory.getLogger(ConsumingRestfulApp.class);

    public static void main(String[] args) {
        SpringApplication.run(ConsumingRestfulApp.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) {
        return args -> {
            Quote quote = restTemplate.getForObject("https://quoters.apps.pcfone.io/api/random", Quote.class);
            assert quote != null;
            logger.info(quote.toString());
        };
    }
}
