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

    public Article(String articleTitle, LocalDate publishDate) {
        Objects.requireNonNull(articleTitle,  "Article Title can not be null");
        Objects.requireNonNull(publishDate,   "Publish Date can not be null");

        this.articleTitle = articleTitle;
        this.publishDate = publishDate;
    }

    @JsonProperty
    public String publishDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return formatter.format(publishDate);
    }

    public  String getArticleTitle() {
        return articleTitle;
    }

    public boolean isPublishedInYear(int publishYear) {
        return publishDate.getYear() == publishYear;
    }

    public int getPublishYear(){
        return publishDate.getYear();
    }
}