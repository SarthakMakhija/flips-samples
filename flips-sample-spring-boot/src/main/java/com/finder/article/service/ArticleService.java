package com.finder.article.service;

import com.finder.article.model.Article;
import com.finder.article.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class ArticleService {

    private ArticleRepository articleRepository;
    private String            separator;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, @Value("${article.name.list.separator}") String separator) {
        this.articleRepository  = articleRepository;
        this.separator          = separator;
    }

    public List<Article> getAllArticles() {
        return articleRepository.getAllArticles();
    }

    public String getAllArticlesRepresentedAsString() {
        return getAllArticles().stream().map(Article::getArticleTitle).sorted().collect(Collectors.joining(separator));
    }

    public List<Article> getArticleByPublishYear(int publishYear) {
        return getAllArticles().stream().filter(movie -> movie.isPublishedInYear(publishYear)).collect(toList());
    }

    public Map<Integer, Long> getArticleStatsByYear() {
        return getAllArticles().stream().collect(Collectors.groupingBy(Article::getPublishYear, Collectors.counting()));
    }
}