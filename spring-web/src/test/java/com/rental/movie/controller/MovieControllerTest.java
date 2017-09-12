package com.rental.movie.controller;


import com.rental.movie.config.ApplicationConfig;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@WebAppConfiguration
@TestPropertySource(properties = {"feature.movie.filter.by.release.year.active=N",
                                  "feature.movie.rating.by.name=Y",
                                  "feature.movie.rating=Y",
                                  "feature.movie.rating.enabled.on.after=2016-10-20T00:00:00Z"
                                 })
public class MovieControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    @Before
    public void setUp(){
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldGetAllMovies() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/movies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(5)));
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
}