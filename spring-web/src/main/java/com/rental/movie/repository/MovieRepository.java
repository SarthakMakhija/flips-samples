package com.rental.movie.repository;

import com.rental.movie.model.Movie;
import com.rental.movie.model.MovieRating;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.rental.movie.model.Movie.Genre.*;

@Repository
public class MovieRepository {

    private List<Movie> movies          = new ArrayList<>();
    private List<MovieRating> ratings   = new ArrayList<>();

    @PostConstruct
    public void initialize(){
        movies.add(new Movie("3-Idiots", LocalDate.of(2009, Month.DECEMBER, 25), COMEDY));
        movies.add(new Movie("Airlift",  LocalDate.of(2016, Month.JANUARY,  22), DRAMA));
        movies.add(new Movie("Dangal",   LocalDate.of(2016, Month.DECEMBER, 21), DRAMA));
        movies.add(new Movie("Sultan",   LocalDate.of(2016, Month.JULY, 06),     ROMANCE));
        movies.add(new Movie("Inception",LocalDate.of(2010, Month.JULY, 16),     ACTION));

        ratings.add(new MovieRating(findByName("Inception").get(), 10));
        ratings.add(new MovieRating(findByName("Sultan").get(),    8));
        ratings.add(new MovieRating(findByName("Airlift").get(),   9));
    }

    public List<Movie> getAllMovies() {
        return new ArrayList<>(movies);
    }

    public MovieRating getMovieRatingByMovieName(String movieName){
        return ratings.stream().filter(movieRating -> movieRating.getMovie().matchesName(movieName)).findFirst().orElse(MovieRating.NONE);
    }

    public List<MovieRating> getAllRatedMovieRatings() {
        return new ArrayList<>(ratings);
    }

    private Optional<Movie> findByName(String movieName){
        return movies.stream().filter(movie -> movie.matchesName(movieName)).findFirst();
    }
}