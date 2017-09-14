package com.finder.article.controller;

import com.finder.article.model.Article;
import com.finder.article.service.ArticleService;
import org.flips.annotation.FlipBean;
import org.flips.annotation.FlipOnEnvironmentProperty;
import org.flips.annotation.FlipOnSpringExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping(value = "/articles/represent", method = RequestMethod.GET, produces = "application/json")
    @FlipOnEnvironmentProperty(property = "feature.article.represent.as.separated", expectedValue = "Y")
    public String getAllArticlesRepresentedAsString(){
        return articleService.getAllArticlesRepresentedAsString();
    }

    @RequestMapping(value = "/articles/{releaseYear}", method = RequestMethod.GET)
    @FlipOnSpringExpression(expression = "@environment.getProperty('feature.article.filter.by.release.year.active') == 'Y'")
    public List<Article> getArticleByPublishYear(@PathVariable int releaseYear){
        return articleService.getArticleByPublishYear(releaseYear);
    }

    @FlipBean(with = ArticleStatsController.class)
    @FlipOnEnvironmentProperty(property = "feature.article.stat.flip.alternate", expectedValue = "Y")
    @RequestMapping(value = "/articles/stats/year", method = RequestMethod.GET)
    public Map<Integer, Long> getArticleStatsByYear(){
        return new HashMap<>();
    }

}