package com.rental.movie.service;

import com.rental.movie.model.Movie;
import com.rental.movie.model.MovieRating;
import com.rental.movie.repository.MovieRepository;
import org.flips.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class MovieService {

    private MovieRepository     movieRepository;
    private MovieBannerService  movieBannerService;
    private String              separator;

    @Autowired
    public MovieService(MovieRepository movieRepository, MovieBannerService movieBannerService, @Value("${movies.name.list.separator}") String separator) {
        this.movieRepository    = movieRepository;
        this.movieBannerService = movieBannerService;
        this.separator          = separator;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.getAllMovies();
    }

    public String getAllMoviesRepresentedAsString() {
        return getAllMovies().stream().map(Movie::getMovieName).sorted().collect(Collectors.joining(separator));
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

    public Map<Movie.Genre, Long> getMovieStatsByGenre() {
        return getAllMovies().stream().collect(Collectors.groupingBy(Movie::getGenre, Collectors.counting()));
    }

    @FlipOnProfiles(activeProfiles = "dev")
    public void addMovie(Movie movie) {
        movieRepository.addMovie(movie);
    }

    public String getBanner(){
        return movieBannerService.getBanner();
    }
}

@Component
class MovieBannerService{

    @FlipOnEnvironmentProperty(property = "feature.movie.banner")
    public String getBanner(){
        return "Movie Rental";
    }
}