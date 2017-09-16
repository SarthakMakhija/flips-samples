package com.finder.article.controller;

import com.finder.article.model.ArticleStatistics;
import com.finder.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleStatisticsController {

    private ArticleService articleService;

    @Autowired
    public ArticleStatisticsController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping(value = "/alt/articles/statistics", method = RequestMethod.GET)
    public ArticleStatistics getArticleStatisticsByYear(){
        return articleService.getArticleStatisticsByYear();
    }
}