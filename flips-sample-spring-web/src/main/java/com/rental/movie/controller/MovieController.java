package com.rental.movie.controller;

import com.rental.movie.model.Movie;
import com.rental.movie.model.MovieRating;
import com.rental.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/movies/{releaseYear}", method = RequestMethod.GET)
    public List<Movie> getMoviesByReleaseYear(@PathVariable int releaseYear){
        return movieService.getMoviesByReleaseYear(releaseYear);
    }

    @RequestMapping(value = "/movies/genres/{genre}")
    public List<Movie> getMoviesByGenre(@PathVariable Movie.Genre genre){
        return movieService.getMoviesByGenre(genre);
    }

    @RequestMapping(value = "/movies/rated/ratings", method = RequestMethod.GET)
    public List<MovieRating> getAllRatedMovieRatings(){
        return movieService.getAllRatedMovieRatings();
    }

    @RequestMapping(value = "/movies/{movieName}/rating", method = RequestMethod.GET)
    public MovieRating getMovieRatingByMovieName(@PathVariable String movieName){
        return movieService.getMovieRatingByMovieName(movieName);
    }
}