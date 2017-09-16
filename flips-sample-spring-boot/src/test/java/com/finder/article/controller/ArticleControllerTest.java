package com.finder.article.controller;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"feature.article.by.id=Y",
                              "feature.movie.statistics=Y"
                             })
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class ArticleControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldLoadAllArticles() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/articles"))
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)));
    }

    @Test
    public void shouldGetArticleById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/articles/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.articleTitle", Matchers.equalTo("Google's Digital Culture")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publishDate", Matchers.equalTo("25 Dec 2009")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publishYear", Matchers.equalTo(2009)));
    }

    @Test
    public void getArticleStatsByYear() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/articles/statistics"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.groupedByYear.2016", Matchers.equalTo(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.groupedByYear.2009", Matchers.equalTo(1)));
    }
}