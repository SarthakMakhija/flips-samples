package com.finder.movie.controller;

import com.finder.movie.model.Movie;
import com.finder.movie.model.MovieRating;
import com.finder.movie.service.MovieService;
import org.flips.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MovieController {

    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public List<Movie> getAllMovies(){
        return movieService.getAllMovies();
    }

    @RequestMapping(value = "/movies/represent", method = RequestMethod.GET, produces = "application/json")
    public String getAllMoviesRepresentedAsString(){
        return movieService.getAllMoviesRepresentedAsString();
    }

    @RequestMapping(value = "/movies/{releaseYear}", method = RequestMethod.GET)
    @FlipOnEnvironmentProperty(property = "feature.movie.filter.by.release.year.active", expectedValue = "Y")
    public List<Movie> getMoviesByReleaseYear(@PathVariable int releaseYear){
        return movieService.getMoviesByReleaseYear(releaseYear);
    }

    @RequestMapping(value = "/movies/genres/{genre}")
    @FlipOff
    public List<Movie> getMoviesByGenre(@PathVariable Movie.Genre genre){
        return movieService.getMoviesByGenre(genre);
    }

    @RequestMapping(value = "/movies/rated/ratings", method = RequestMethod.GET)
    @FlipOnSpringExpression(expression = "@environment.getProperty('feature.movie.rating') == 'Y'")
    @FlipOnDateTime        (cutoffDateTimeProperty = "feature.movie.rating.enabled.on.after")
    public List<MovieRating> getAllRatedMovieRatings(){
        return movieService.getAllRatedMovieRatings();
    }

    @RequestMapping(value = "/movies/{movieName}/rating", method = RequestMethod.GET)
    @FlipOnEnvironmentProperty(property = "feature.movie.rating.by.name", expectedValue = "Y")
    public MovieRating getMovieRatingByMovieName(@PathVariable String movieName){
        return movieService.getMovieRatingByMovieName(movieName);
    }

    @FlipBean(with = MovieStatsController.class)
    @FlipOnEnvironmentProperty(property = "feature.movie.stat.flip.alternate", expectedValue = "Y")
    @RequestMapping(value = "/movies/stats/genre", method = RequestMethod.GET)
    public Map<Movie.Genre, Long> getMovieStatsByGenre(){
        return new HashMap<>();
    }

    @RequestMapping(value = "/movies/banner", method = RequestMethod.GET)
    public String getBanner(){
        return movieService.getBanner();
    }

    @RequestMapping(value = "/movies", method = RequestMethod.PUT)
    @FlipOnProfiles(activeProfiles = "dev")
    public void addMovie(@RequestBody Movie movie){
        movieService.addMovie(movie);
    }
}