package com.finder.movie.controller;

import com.finder.movie.model.Movie;
import com.finder.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MovieStatsController {

    private MovieService movieService;

    @Autowired
    public MovieStatsController(MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping(value = "/alt/movies/stats/genre", method = RequestMethod.GET)
    public Map<Movie.Genre, Long> getMovieStatsByGenre(){
        return movieService.getMovieStatsByGenre();
    }
}