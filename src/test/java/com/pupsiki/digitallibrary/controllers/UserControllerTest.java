package com.pupsiki.digitallibrary.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void registrationPage() throws Exception{
        this.mockMvc.perform(get("/registration")).
                andDo(print()).
                andExpect(status().isOk()).
                andExpect(model().attribute("view", "registration")).
                andExpect(model().attribute("title", "Регистрация")).
                andExpect(model().attributeExists("user"));
    }


    @Test
    public void user() throws Exception {
        this.mockMvc.perform(get("/user"))
                .andExpect(redirectedUrl("http://localhost/ulogin"));
    }

    @Test
    public void loginPage() throws Exception{
        this.mockMvc.perform(get("/ulogin")).
                andDo(print()).
                andExpect(status().isOk()).
                andExpect(model().attribute("view", "login")).
                andExpect(model().attribute("title", "Вход"));
    }

    @Test
    public void registration() {
    }

    @Test
    public void confirmRegistration() throws Exception {
        this.mockMvc.perform(get("/registrationConfirm").
                            param("token", "")).
                andDo(print()).
                andExpect(status().isFound());

    }
}