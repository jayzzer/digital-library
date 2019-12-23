package com.pupsiki.digitallibrary.controllers;

import com.pupsiki.digitallibrary.models.Book;
import com.pupsiki.digitallibrary.models.CustomUserDetails;
import com.pupsiki.digitallibrary.models.Deal;
import com.pupsiki.digitallibrary.models.User;
import com.pupsiki.digitallibrary.repositories.BookRepository;
import com.pupsiki.digitallibrary.repositories.DealRepository;
import com.pupsiki.digitallibrary.controllers.BookController;
import com.pupsiki.digitallibrary.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
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
    private UserRepository userRepository;

    @Test
    //@WithMockUser(username = "user1", password = "1234", roles = "USER")
    public void bookAlreadyBought() throws Exception {
        //Authentication authentication = Mockito.mock(Authentication.class);
        //authentication.setAuthenticated(true);

        //Model model = Mockito.mock(Model.class);

        Book book = new Book();
        book.setId(115);
        book.setTitle("книга");

        //BookController.book(model, book.getId(), authentication);

        User user = Mockito.mock(User.class);
        user.setId(anyInt());

        Mockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        //prepare list
        List<Integer> userBooks = new ArrayList<Integer>();
        userBooks.add(book.getId());

        Mockito.when(dealRepository.getUserBooks(user.getId())).thenReturn(userBooks);
        //boolean alreadyBought = false;
        //Mockito.when(userBooks.contains(book.getId())).thenReturn(alreadyBought = true);

        this.mockMvc.perform(get("/books/115")).
                andDo(print()).
                andExpect(status().isOk()).
                andExpect(model().attribute("view", "book")).
                andExpect(model().attribute("title", "книга")).
                andExpect(model().attribute("book", book)).
                andExpect(model().attribute("alreadyBought", false)).
                andExpect(model().attribute("stripePublicKey", "pk_test_EWzgpRWrtFbuBOFoLTOd2rDU00GS8vLpY7"));
    }

    @Test
    public void bookNotBought() throws Exception {
//        //prepare book already bought
//        Book book = new Book();
//        book.setId(115);
//        book.setTitle("AlreadyBought");
//
//        //prepare user
//        User user = new User();
//
//        //prepare list
//        List<Integer> userBooks = new ArrayList<Integer>();
//        userBooks.add(115);
//
//        Mockito.when(dealRepository.getUserBooks(user.getId())).thenReturn(userBooks);
//        boolean alreadyBought = false;
//        if (userBooks.contains(book.getId())) {
//            alreadyBought = true;
//        }
//        Mockito.when(bookRepository.findById(115)).thenReturn(Optional.of(book));
//        this.mockMvc.perform(get("/books/115")).
//                andDo(print()).
//                andExpect(status().isOk()).
//                andExpect(model().attribute("view", "book")).
//                andExpect(model().attribute("title", "AlreadyBought")).
//                andExpect(model().attribute("book", book)).
//                andExpect(model().attribute("alreadyBought", alreadyBought)).
//                andExpect(model().attribute("stripePublicKey", "pk_test_EWzgpRWrtFbuBOFoLTOd2rDU00GS8vLpY7"));
    }

    @Test
    public void searchBooks() throws Exception {
        List<Book> list = new ArrayList<Book>();
        list.add(new Book("bookname", "book1.jpg", "author", "romashka", "genre", "dwacr", 1999, 235, 1234f));
        Page<Book> page = new PageImpl<Book>(list, PageRequest.of(1, 12), 1);
        Page<Book> books = Mockito.mock(Page.class);
//        Mockito.when(bookRepository.findAllByTitleContainingIgnoreCase("", PageRequest.of(1, 12))).thenReturn(page);
        this.mockMvc.perform(get("/books/search").
                param("text", "man").
                param("p", "1")).
                andDo(print()).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("view")).
                andExpect(model().attributeExists("title")).
                andExpect(model().attributeExists("books"));
    }

    @Test
    public void buyBook() throws Exception {
        Book book = new Book();
        book.setId(anyInt());

        Mockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        // User user = Mockito.mock(User.class);
        User user = new User();
        user.setId(123);
        user.setRoles("USER");

        CustomUserDetails userDetails = new CustomUserDetails(user);

        Mockito.when(userRepository.findById(userDetails.getId())).thenReturn(Optional.of(user));

//        Deal deal = new Deal();
//        deal.setBook(book);
//        deal.setUser(user);

//        Mockito.verify(dealRepository, Mockito.times(1)).save(new Deal(user, book));
        this.mockMvc.perform(post("/charge")).
                andDo(print()).
                andExpect(status().isForbidden());
        //andExpect(redirectedUrl("/books/115?success"));
    }

    @Test
    public void books() throws Exception {
//        this.mockMvc.perform(post("/charge")).
//                andDo(print()).
//                andExpect(status().isOk()).
//                andExpect(model().attributeExists("view")).
//                andExpect(model().attributeExists("title")).
//                andExpect(model().attributeExists("books")).
//                andExpect(model().attributeExists("categories"));
    }
}