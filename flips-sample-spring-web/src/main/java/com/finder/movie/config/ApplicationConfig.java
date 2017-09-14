package com.finder.movie.config;

import org.flips.describe.config.FlipWebContextConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = "com.finder.movie")
@Import       (FlipWebContextConfiguration.class)
@EnableWebMvc
@PropertySource("classpath:config.properties")
public class ApplicationConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer placeholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        return placeholderConfigurer;
    }
}