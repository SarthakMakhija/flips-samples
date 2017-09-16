package com.finder.movie.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.finder.movie.config.ApplicationConfig;
import com.finder.movie.model.Movie;
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
@TestPropertySource(properties = {"feature.movie.filter.by.id=N",
                                  "feature.movie.rating=Y",
                                  "feature.movie.rating.enabled.on.after=2016-10-20T00:00:00Z",
                                  "feature.movie.stat.flip.alternate=Y"
                                 })
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
    public void shouldGetFeatureNotEnabledOnMovieById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/movies/1"))
                .andExpect(MockMvcResultMatchers.status().is(501))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage", Matchers.equalTo("Feature not enabled, identified by method public com.finder.movie.model.Movie com.finder.movie.controller.MovieController.getMovieById(int)")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.className",    Matchers.equalTo("com.finder.movie.controller.MovieController")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.featureName",  Matchers.equalTo("getMovieById")));
    }

    @Test
    public void shouldGetFeatureNotEnabledOnMovieGenre() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/movies/1/genre"))
                .andExpect(MockMvcResultMatchers.status().is(501))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage", Matchers.equalTo("Feature not enabled, identified by method public com.finder.movie.model.Movie$Genre com.finder.movie.controller.MovieController.getMovieGenre(int)")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.className",    Matchers.equalTo("com.finder.movie.controller.MovieController")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.featureName",  Matchers.equalTo("getMovieGenre")));
    }

    @Test
    public void shouldGetAllRatedMovieRatings() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/movies/5/rating"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.movie.genre",          Matchers.equalTo(Movie.Genre.ACTION.toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movie.id",             Matchers.equalTo(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movie.movieName",      Matchers.equalTo("Inception")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rating",               Matchers.equalTo(10)));
    }

    @Test
    public void shouldGetMovieStatistics() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/movies/statistics"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.groupByGenre.ACTION",     Matchers.equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.groupByGenre.ROMANCE",    Matchers.equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.groupByGenre.DRAMA",      Matchers.equalTo(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.groupByGenre.COMEDY",     Matchers.equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.groupByReleaseYear.2016",     Matchers.equalTo(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.groupByReleaseYear.2009",     Matchers.equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.groupByReleaseYear.2010",     Matchers.equalTo(1)));
    }

}