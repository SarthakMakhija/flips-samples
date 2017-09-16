package com.finder.movie.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Movie {

    public enum Genre {
        ACTION, ROMANCE, DRAMA, COMEDY
    }

    @JsonProperty
    private int id;

    @JsonProperty
    private final String    movieName;

    @JsonIgnore
    private final LocalDate releaseDate;

    @JsonProperty
    @JsonUnwrapped
    private Genre genre;

    public Movie(int id, String movieName, LocalDate releaseDate, Genre genre) {
        Objects.requireNonNull(movieName,   "Movie Name can not be null");
        Objects.requireNonNull(releaseDate, "Release Date can not be null");
        Objects.requireNonNull(genre,       "Genre can not be null");

        this.id             = id;
        this.genre          = genre;
        this.movieName      = movieName;
        this.releaseDate    = releaseDate;
    }

    public boolean equalsId(int id){
        return this.id == id;
    }

    public boolean matchesName(String movieName) {
        return this.movieName.equals(movieName);
    }

    @JsonProperty
    public String releaseDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return formatter.format(releaseDate);
    }

    public int releaseYear(){
        return releaseDate.getYear();
    }

    public Genre getGenre(){
        return genre;
    }

    public String getMovieName() {
        return movieName;
    }
}