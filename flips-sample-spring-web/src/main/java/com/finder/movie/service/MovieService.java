package com.finder.movie.service;

import com.finder.movie.model.Movie;
import com.finder.movie.model.MovieRating;
import com.finder.movie.model.MovieStatistics;
import com.finder.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private MovieRepository     movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository    = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.getAllMovies();
    }

    public Optional<Movie> getMovieById(int id){
        return getAllMovies().stream().filter(movie -> movie.equalsId(id)).findFirst();
    }

    public Optional<Movie.Genre> getMovieGenre(int id) {
        return getMovieById(id).map(Movie::getGenre);
    }

    public Optional<MovieRating> getMovieRating(int id) {
         return getMovieById(id).map(this::getMovieRatingByMovieName);
    }

    public MovieRating getMovieRatingByMovieName(Movie movie) {
        return movieRepository.getMovieRatingByMovieName(movie.getMovieName());
    }

    public MovieStatistics getMovieStatistics() {
        Map<Movie.Genre, Long> groupByGenre   = getAllMovies().stream().collect(Collectors.groupingBy(Movie::getGenre, Collectors.counting()));
        Map<Integer, Long> groupByReleaseYear = getAllMovies().stream().collect(Collectors.groupingBy(Movie::releaseYear, Collectors.counting()));
        return new MovieStatistics(groupByGenre, groupByReleaseYear);
    }
}