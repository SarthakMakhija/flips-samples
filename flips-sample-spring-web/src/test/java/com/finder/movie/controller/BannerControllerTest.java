package com.finder.movie.controller;

import com.finder.movie.config.ApplicationConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@WebAppConfiguration
@TestPropertySource(properties = {"feature.application.banner=false"})
public class BannerControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mvc;

    @Before
    public void setUp(){
        mvc    = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldGetFeatureNotEnabledOnBanner() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/banner"))
           .andExpect(MockMvcResultMatchers.status().is(501));
    }
}