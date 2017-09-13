package com.finder.movie.config;

import org.flips.describe.config.FlipWebContextConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = "com.finder.movie")
@Import       (FlipWebContextConfiguration.class)
@EnableWebMvc
public class ApplicationConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer placeholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources                                       = new ClassPathResource[]{ new ClassPathResource( "config.properties" ) };
        placeholderConfigurer.setLocations( resources );

        return placeholderConfigurer;
    }
}