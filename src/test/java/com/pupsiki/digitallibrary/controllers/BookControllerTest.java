package com.pupsiki.digitallibrary.controllers;

import com.pupsiki.digitallibrary.models.Book;
import com.pupsiki.digitallibrary.models.User;
import com.pupsiki.digitallibrary.repositories.BookRepository;
import com.pupsiki.digitallibrary.repositories.DealRepository;
import com.pupsiki.digitallibrary.repositories.UserRepository;
import com.pupsiki.digitallibrary.services.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private DealRepository dealRepository;

    @MockBean
    private BookService bookService;

    @Autowired
    private BookController bookController;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void book() throws Exception {
        Book book = new Book();
        book.setId(115);
        book.setTitle("книжонка");

        User user = Mockito.mock(User.class);
        user.setId(636);

        Mockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        List<Integer> userBooks = new ArrayList<Integer>();
        userBooks.add(book.getId());

        Mockito.when(dealRepository.getUserBooks(user.getId())).thenReturn(userBooks);

        this.mockMvc.perform(get("/books/{id}",book.getId()).
                param(" Authentication", "true")).
                andDo(print()).
                andExpect(status().isOk()).
                andExpect(model().attribute("view", "book")).
                andExpect(model().attribute("title", "книжонка")).
                andExpect(model().attribute("book", book)).
                andExpect(model().attribute("stripePublicKey", "pk_test_EWzgpRWrtFbuBOFoLTOd2rDU00GS8vLpY7"));
    }

    @Test
    public void searchBooksNotEmpty() throws Exception {
        List<Book> list = new ArrayList<Book>();
        list.add(new Book("bookname", "book1.jpg", "author", "romashka", "genre", "dwacr", 1999, 235, 1234f));
        Page<Book> page = new PageImpl<Book>(list, PageRequest.of(1, 12), 1);

        Mockito.when(bookService.findBooks("man", 1, 15)).thenReturn(page);

        this.mockMvc.perform(get("/books/search").
                param("text", "man").
                param("p", "1")).
                andDo(print()).
                andExpect(status().isOk()).
                andExpect(model().attribute("view", "search")).
                andExpect(model().attribute("title", "Поиск")).
                andExpect(model().attribute("books", page));
    }

    @Test
    public void searchBooksJSON() {
    }

    @Test
    public void buyBook() throws Exception {
    }

    @Test
    public void books() throws Exception {
        this.mockMvc.perform(get("/books").
                param("genre","all").
                param("sort", "rating-d").
                param("p", "0")).
                andDo(print()).
                andExpect(status().isOk());

        this.mockMvc.perform(get("/books").
                param("genre","all").
                param("sort", "rating-u").
                param("p", "0")).
                andDo(print()).
                andExpect(status().isOk());


    }
}
