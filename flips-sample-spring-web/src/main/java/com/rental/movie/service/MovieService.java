package com.rental.movie.service;

import com.rental.movie.model.Movie;
import com.rental.movie.model.MovieRating;
import com.rental.movie.repository.MovieRepository;
import org.flips.annotation.FlipOff;
import org.flips.annotation.FlipOnDateTime;
import org.flips.annotation.FlipOnEnvironmentProperty;
import org.flips.annotation.FlipOnSpringExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class MovieService {

    private MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.getAllMovies();
    }

    @FlipOnEnvironmentProperty(property = "feature.movie.filter.by.release.year.active", expectedValue = "Y")
    public List<Movie> getMoviesByReleaseYear(int year){
        return getAllMovies().stream().filter(movie -> movie.isReleasedInYear(year)).collect(toList());
    }

    @FlipOff
    public List<Movie> getMoviesByGenre(Movie.Genre genre) {
        return getAllMovies().stream().filter(movie -> movie.matchesGenre(genre)).collect(toList());
    }

    @FlipOnSpringExpression(expression = "@environment.getProperty('feature.movie.rating') == 'Y'")
    @FlipOnDateTime        (cutoffDateTimeProperty = "feature.movie.rating.enabled.on.after")
    public List<MovieRating> getAllRatedMovieRatings() {
        return movieRepository.getAllRatedMovieRatings();
    }

    @FlipOnEnvironmentProperty(property = "feature.movie.rating.by.name", expectedValue = "Y")
    public MovieRating getMovieRatingByMovieName(String movieName) {
        return movieRepository.getMovieRatingByMovieName(movieName);
    }
}