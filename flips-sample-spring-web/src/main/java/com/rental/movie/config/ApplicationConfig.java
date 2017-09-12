package com.rental.movie.config;

import org.flips.describe.config.FlipWebContextConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = "com.rental.movie")
@Import       (FlipWebContextConfiguration.class)
@EnableWebMvc
public class ApplicationConfig {
}