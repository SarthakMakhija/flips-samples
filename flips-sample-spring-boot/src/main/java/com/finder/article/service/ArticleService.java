package com.finder.article.service;

import com.finder.article.model.Article;
import com.finder.article.model.ArticleStatistics;
import com.finder.article.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository  = articleRepository;
    }

    public List<Article> getAllArticles() {
        return articleRepository.getAllArticles();
    }

    public Optional<Article> getArticleById(int id) {
        return getAllArticles().stream().filter(article -> article.equalsId(id)).findFirst();
    }

    public ArticleStatistics getArticleStatisticsByYear() {
        return new ArticleStatistics(getAllArticles().stream().collect(Collectors.groupingBy(Article::getPublishYear, Collectors.counting())));
    }
}