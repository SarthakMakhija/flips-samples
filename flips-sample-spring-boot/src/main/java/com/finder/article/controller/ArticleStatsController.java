package com.finder.article.controller;

import com.finder.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ArticleStatsController {

    private ArticleService articleService;

    @Autowired
    public ArticleStatsController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping(value = "/alt/articles/stats/genre", method = RequestMethod.GET)
    public Map<Integer, Long> getArticleStatsByYear(){
        return articleService.getArticleStatsByYear();
    }
}