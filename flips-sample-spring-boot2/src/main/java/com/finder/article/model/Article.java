package com.finder.article.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Article {

    @JsonProperty
    private final String articleTitle;

    @JsonIgnore
    private final LocalDate publishDate;

    private int id;

    public Article(int id, String articleTitle, LocalDate publishDate) {
        Objects.requireNonNull(articleTitle,  "Article Title can not be null");
        Objects.requireNonNull(publishDate,   "Publish Date can not be null");

        this.id = id;
        this.articleTitle = articleTitle;
        this.publishDate = publishDate;
    }

    @JsonProperty
    public String publishDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return formatter.format(publishDate);
    }

    public int getPublishYear(){
        return publishDate.getYear();
    }

    public boolean equalsId(int id){
        return this.id == id;
    }
}