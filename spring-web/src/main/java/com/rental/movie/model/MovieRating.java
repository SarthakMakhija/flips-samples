package com.rental.movie.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieRating {

    public static final MovieRating NONE = new MovieRating();

    @JsonProperty
    private Movie movie;
    @JsonProperty
    private int rating;

    private MovieRating() {
    }

    public MovieRating(Movie movie, int rating) {
        this.movie  = movie;
        this.rating = rating;
    }

    public Movie getMovie(){
        return movie;
    }
}