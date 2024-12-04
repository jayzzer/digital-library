package com.pupsiki.digitallibrary.controllers;

import com.pupsiki.digitallibrary.models.Book;
import com.pupsiki.digitallibrary.repositories.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MainController controller;

    @MockBean
    private BookRepository bookRepository;

    @Test
    public void HomeTest() throws Exception{
        List<Book> topFreeBooks = new ArrayList<Book>();
        List<Book> topNew = new ArrayList<Book>();
        List<Book> topBooks = new ArrayList<Book>();

        Mockito.when(bookRepository.findTop10ByPriceOrderByRatingDesc(0f)).thenReturn(topFreeBooks);
        Mockito.when(bookRepository.findTop10ByOrderByCreatedAtDesc()).thenReturn(topNew);
        Mockito.when(bookRepository.findTop10ByOrderByRatingDesc()).thenReturn(topBooks);

        this.mockMvc.perform(get("/")).
                andDo(print()).
                andExpect(status().isOk()).
                andExpect(model().attribute("view", "index")).
                andExpect(model().attribute("title", "Главная страница")).
                andExpect(model().attribute("topFreeBooks", topFreeBooks)).
                andExpect(model().attribute("topNew", topNew)).
                andExpect(model().attribute("topBooks", topBooks));
    }

}