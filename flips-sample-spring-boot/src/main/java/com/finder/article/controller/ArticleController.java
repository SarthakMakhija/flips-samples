package com.finder.article.controller;

import com.finder.article.model.Article;
import com.finder.article.model.ArticleStatistics;
import com.finder.article.service.ArticleService;
import org.flips.annotation.FlipBean;
import org.flips.annotation.FlipOnEnvironmentProperty;
import org.flips.annotation.FlipOnSpringExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController {

    private ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping(value = "/articles", method = RequestMethod.GET)
    public List<Article> getAllArticles(){
        return articleService.getAllArticles();
    }

    @RequestMapping(value = "/articles/{id}", method = RequestMethod.GET, produces = "application/json")
    @FlipOnEnvironmentProperty(property = "feature.article.by.id", expectedValue = "Y")
    public Article getArticleById(@PathVariable int id){
        return articleService.getArticleById(id).orElse(null);
    }

    @FlipBean(with = ArticleStatisticsController.class)
    @FlipOnSpringExpression(expression = "@environment.getProperty('feature.movie.statistics') == 'Y'")
    @RequestMapping(value = "/articles/statistics", method = RequestMethod.GET)
    public ArticleStatistics getArticleStatisticsByYear(){
        return ArticleStatistics.NONE;
    }
}