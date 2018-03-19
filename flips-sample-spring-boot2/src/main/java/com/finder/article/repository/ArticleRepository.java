package com.finder.article.repository;

import com.finder.article.model.Article;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleRepository {

    private List<Article> articles = new ArrayList<>();

    @PostConstruct
    public void initialize(){
        articles.add(new Article(1, "Google's Digital Culture",      LocalDate.of(2009, Month.DECEMBER, 25)));
        articles.add(new Article(2, "Self Driving Cars",             LocalDate.of(2016, Month.JANUARY,  22)));
        articles.add(new Article(3, "AI - a boon or destruction",    LocalDate.of(2016, Month.DECEMBER, 21)));
    }

    public List<Article> getAllArticles() {
        return new ArrayList<>(articles);
    }
}
