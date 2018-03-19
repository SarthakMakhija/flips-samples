package com.finder.article.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ArticleStatistics {

    public static final ArticleStatistics NONE = new ArticleStatistics(Collections.EMPTY_MAP);

    @JsonProperty
    private Map<Integer, Long>  groupedByYear = new HashMap<>();

    public ArticleStatistics(Map<Integer, Long> groupedByYear) {
        this.groupedByYear.putAll(groupedByYear);
    }
}