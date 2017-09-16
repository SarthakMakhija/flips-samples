package com.finder.movie.controller;

import com.finder.movie.model.MovieStatistics;
import com.finder.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieStatisticsController {

    private MovieService movieService;

    @Autowired
    public MovieStatisticsController(MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping(value = "/alt/movies/statistics", method = RequestMethod.GET)
    public MovieStatistics getMovieStatistics(){
        return movieService.getMovieStatistics();
    }
}