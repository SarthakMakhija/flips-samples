package com.rental.movie.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rental.movie.config.ApplicationConfig;
import com.rental.movie.model.Movie;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.Month;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@WebAppConfiguration
@TestPropertySource(properties = {"feature.movie.filter.by.release.year.active=N",
                                  "feature.movie.rating.by.name=Y",
                                  "feature.movie.rating=Y",
                                  "feature.movie.rating.enabled.on.after=2016-10-20T00:00:00Z",
                                  "feature.movie.stat.flip.alternate=Y",
                                  "feature.movie.banner=false"
                                 })
@ActiveProfiles("qa")
public class MovieControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc      mvc;
    private ObjectWriter writer;

    @Before
    public void setUp(){
        writer = new ObjectMapper().writer();
        mvc    = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldGetAllMovies() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/movies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(5)));
    }

    @Test
    public void shouldGetAllMoviesRepresentedAsString() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/movies/represent"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("3-Idiots,Airlift,Dangal,Inception,Sultan"));
    }

    @Test
    public void shouldGetFeatureNotEnabledOnMoviesByReleaseYear() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/movies/2016"))
                .andExpect(MockMvcResultMatchers.status().is(501))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage", Matchers.equalTo("Feature not enabled, identified by method public java.util.List com.rental.movie.service.MovieService.getMoviesByReleaseYear(int)")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.className",    Matchers.equalTo("com.rental.movie.service.MovieService")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.featureName",  Matchers.equalTo("getMoviesByReleaseYear")));
    }

    @Test
    public void shouldGetFeatureNotEnabledOnMoviesByGenre() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/movies/genres/ACTION"))
                .andExpect(MockMvcResultMatchers.status().is(501))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage", Matchers.equalTo("Feature not enabled, identified by method public java.util.List com.rental.movie.service.MovieService.getMoviesByGenre(com.rental.movie.model.Movie$Genre)")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.className",    Matchers.equalTo("com.rental.movie.service.MovieService")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.featureName",  Matchers.equalTo("getMoviesByGenre")));
    }

    @Test
    public void shouldGetAllRatedMovieRatings() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/movies/rated/ratings"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)));
    }

    @Test
    public void shouldGetMovieRatingByName() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/movies/Inception/rating"))
              .andExpect(MockMvcResultMatchers.status().isOk())
              .andExpect(MockMvcResultMatchers.jsonPath("$.movie.releaseDate", Matchers.equalTo("16 Jul 2010")))
              .andExpect(MockMvcResultMatchers.jsonPath("$.movie.movieName",   Matchers.equalTo("Inception")))
              .andExpect(MockMvcResultMatchers.jsonPath("$.rating",            Matchers.equalTo(10)));
    }

    @Test
    public void shouldGetMovieStats() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/movies/stats/genre"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.COMEDY",   Matchers.equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ACTION",   Matchers.equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.DRAMA",    Matchers.equalTo(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ROMANCE",  Matchers.equalTo(1)));
    }

    @Test
    public void shouldGetFeatureNotEnabledOnGettingBanner() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/movies/banner"))
                .andExpect(MockMvcResultMatchers.status().is(501));
    }

    @Test
    public void shouldGetFeatureNotEnabledOnAddingMovie() throws Exception {
        String content = writer.writeValueAsString(new Movie("The Expendables", LocalDate.of(2010, Month.AUGUST, 13), Movie.Genre.ACTION));
        mvc.perform(MockMvcRequestBuilders.post("/movies/genres/ACTION", content))
                .andExpect(MockMvcResultMatchers.status().is(501));
    }
}