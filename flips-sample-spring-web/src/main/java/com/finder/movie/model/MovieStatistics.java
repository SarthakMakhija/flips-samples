package com.finder.movie.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MovieStatistics {

    @JsonProperty
    private final Map<Movie.Genre, Long> groupByGenre   = new HashMap<>();
    @JsonProperty
    private final Map<Integer, Long> groupByReleaseYear = new HashMap<>();

    public static final MovieStatistics NONE = new MovieStatistics(Collections.EMPTY_MAP, Collections.EMPTY_MAP);

    public MovieStatistics(Map<Movie.Genre, Long> groupByGenre, Map<Integer, Long> groupByReleaseYear) {
        this.groupByGenre.putAll(groupByGenre);
        this.groupByReleaseYear.putAll(groupByReleaseYear);
    }
}
