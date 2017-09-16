package com.finder.movie.controller;

import com.finder.movie.model.Movie;
import com.finder.movie.model.MovieRating;
import com.finder.movie.model.MovieStatistics;
import com.finder.movie.service.MovieService;
import org.flips.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.GET)
    @FlipOnEnvironmentProperty(property = "feature.movie.filter.by.id", expectedValue = "Y")
    public Movie getMovieById(@PathVariable int id){
        return movieService.getMovieById(id).orElse(null);
    }

    @RequestMapping(value = "/movies/{id}/genre")
    @FlipOff
    public Movie.Genre getMovieGenre(@PathVariable int id){
        return movieService.getMovieGenre(id).orElse(null);
    }

    @RequestMapping(value = "/movies/{id}/rating", method = RequestMethod.GET)
    @FlipOnSpringExpression(expression = "@environment.getProperty('feature.movie.rating') == 'Y'")
    @FlipOnDateTime        (cutoffDateTimeProperty = "feature.movie.rating.enabled.on.after")
    public MovieRating getAllRatedMovieRatings(@PathVariable int id){
        return movieService.getMovieRating(id).orElse(null);
    }

    @FlipBean(with = MovieStatisticsController.class)
    @FlipOnEnvironmentProperty(property = "feature.movie.stat.flip.alternate", expectedValue = "Y")
    @RequestMapping(value = "/movies/statistics", method = RequestMethod.GET)
    public MovieStatistics getMovieStatistics(){
        return MovieStatistics.NONE;
    }
}