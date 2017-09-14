package com.finder.article.controller;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"feature.article.represent.as.separated=Y",
                              "feature.article.stat.flip.alternate=Y"
                             })
@AutoConfigureMockMvc
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
    public void shouldGetAllArticlesRepresentedAsString() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/articles/represent"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("AI - a boon or destruction,Google's Digital Culture,Self Driving Cars"));
    }

    @Test
    public void shouldGetAllArticlesByReleaseYear() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/articles/2016"))
                .andExpect(MockMvcResultMatchers.status().is(501))
                .andExpect(MockMvcResultMatchers.content().string("DISABLED"));
    }

    @Test
    public void getArticleStatsByYear() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/articles/stats/year"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.2016", Matchers.equalTo(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.2009", Matchers.equalTo(1)));
    }
}